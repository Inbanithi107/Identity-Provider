package com.techforge.identityprovider.configuration.totp;

import org.jspecify.annotations.Nullable;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class TotpAuthenticationProvider implements AuthenticationProvider {
    @Override
    public @Nullable Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(TotpAuthenticationToken.class);
    }
}
