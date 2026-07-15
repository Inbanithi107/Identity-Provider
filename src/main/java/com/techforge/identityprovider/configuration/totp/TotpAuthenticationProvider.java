package com.techforge.identityprovider.configuration.totp;

import com.techforge.identityprovider.dto.SecurityUser;
import com.techforge.identityprovider.exception.AppException;
import com.techforge.identityprovider.service.TotpService;
import org.jspecify.annotations.Nullable;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class TotpAuthenticationProvider implements AuthenticationProvider {

    private final TotpService totpService;

    public TotpAuthenticationProvider(TotpService totpService) {
        this.totpService = totpService;
    }

    @Override
    public @Nullable Authentication authenticate(Authentication authentication) throws AuthenticationException {
        TotpAuthenticationToken token = (TotpAuthenticationToken) authentication;
        SecurityUser user = (SecurityUser) token.getPrincipal();
        System.out.println(user.getUser().getSecret());
        try{
            System.out.println("1");
            boolean verified = totpService.verifyCode(token.getCode(), user.getUser().getSecret());
            if(verified){
                return new TotpAuthenticationToken(user);
            }else{
                throw new AppException("wrong totp code");
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return TotpAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
