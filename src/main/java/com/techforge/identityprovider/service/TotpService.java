package com.techforge.identityprovider.service;

import dev.samstevens.totp.code.CodeVerifier;
import dev.samstevens.totp.code.HashingAlgorithm;
import dev.samstevens.totp.exceptions.QrGenerationException;
import dev.samstevens.totp.qr.QrData;
import dev.samstevens.totp.qr.QrGenerator;
import dev.samstevens.totp.secret.SecretGenerator;
import dev.samstevens.totp.util.Utils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TotpService {

    private final SecretGenerator secretGenerator;

    private final QrGenerator qrGenerator;

    private final CodeVerifier codeVerifier;

    @Value("${spring.application.name}")
    private String name;

    public TotpService(SecretGenerator secretGenerator, QrGenerator qrGenerator, CodeVerifier codeVerifier) {
        this.secretGenerator = secretGenerator;
        this.qrGenerator = qrGenerator;
        this.codeVerifier = codeVerifier;
    }

    public String generateSecret(){
        return secretGenerator.generate();
    }

    public String generateQrString(String email, String secret) {
        QrData data = getQrDataForUser(email, secret);
        byte[] imageData;
        try {
            imageData = qrGenerator.generate(data);
        } catch (QrGenerationException e) {
            throw new RuntimeException(e);
        }
        return Utils.getDataUriForImage(imageData, qrGenerator.getImageMimeType());
    }

    private QrData getQrDataForUser(String email, String secret){
        return new QrData.Builder()
                .label(email)
                .secret(secret)
                .algorithm(HashingAlgorithm.SHA1)
                .digits(6)
                .issuer(name)
                .period(30)
                .build();
    }

    public boolean verifyCode(String code, String secret){
        return codeVerifier.isValidCode(secret, code);
    }

}
