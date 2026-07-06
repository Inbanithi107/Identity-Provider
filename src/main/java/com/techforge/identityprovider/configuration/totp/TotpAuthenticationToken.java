package com.techforge.identityprovider.configuration.totp;

import com.techforge.identityprovider.dto.SecurityUser;
import org.jspecify.annotations.Nullable;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;

public class TotpAuthenticationToken extends AbstractAuthenticationToken {

    private final int code;

    private final SecurityUser user;

    public TotpAuthenticationToken(SecurityUser user, int code, Authentication authentication){
        super(authentication.getAuthorities());
        this.user = user;
        this.code = code;
    }

    @Override
    public @Nullable Object getCredentials() {
        return null;
    }

    @Override
    public @Nullable Object getPrincipal() {
        return user;
    }

    public int getCode(){
        return code;
    }
}
