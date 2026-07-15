package com.techforge.identityprovider.configuration.totp;

import com.techforge.identityprovider.dto.SecurityUser;
import org.jspecify.annotations.Nullable;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;

public class TotpAuthenticationToken extends AbstractAuthenticationToken {

    private String code;

    private final SecurityUser user;

    public TotpAuthenticationToken(SecurityUser user, String code){
        super(user.getAuthorities());
        this.user = user;
        this.code = code;
        setAuthenticated(false);
    }

    public TotpAuthenticationToken(SecurityUser user){
        super(user.getAuthorities());
        this.user = user;
        setAuthenticated(true);
    }

    @Override
    public @Nullable Object getCredentials() {
        return null;
    }

    @Override
    public @Nullable Object getPrincipal() {
        return user;
    }

    public String getCode(){
        return code;
    }
}
