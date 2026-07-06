package com.techforge.identityprovider.initializer;

import com.nimbusds.jose.jwk.RSAKey;
import com.techforge.identityprovider.entity.Jwk;
import com.techforge.identityprovider.repository.JwkRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

@Component
public class JwkInitializer {

    private final JwkRepository jwkRepository;

    public JwkInitializer(JwkRepository jwkRepository) {
        this.jwkRepository = jwkRepository;
    }
    public Jwk getJwk() {
        return jwkRepository.findTopByOrderByIdAsc().orElseGet(()->{
            KeyPair keyPair = generateRsaKey();
            RSAKey rsaKey = new RSAKey.Builder((RSAPublicKey) keyPair.getPublic())
                    .privateKey((RSAPrivateKey) keyPair.getPrivate())
                    .keyID(UUID.randomUUID().toString())
                    .build();

            return jwkRepository.save(new Jwk().setKeyString(rsaKey.toJSONString()));
        });

    }

    public static KeyPair generateRsaKey() {
        try {
            KeyPairGenerator keyPairGenerator =
                    KeyPairGenerator.getInstance("RSA");

            keyPairGenerator.initialize(2048);
            return keyPairGenerator.generateKeyPair();

        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
    }
}
