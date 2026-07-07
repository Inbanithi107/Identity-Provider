package com.techforge.identityprovider.configuration.system;

import com.techforge.identityprovider.dto.SecurityUser;
import org.jspecify.annotations.Nullable;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;

public class SystemAuthenticationToken extends AbstractAuthenticationToken {

    private final SecurityUser user;

    public SystemAuthenticationToken(Authentication authentication, SecurityUser user){
        super(authentication.getAuthorities());
        this.user = user;
    }
    @Override
    public @Nullable Object getCredentials() {
        return null;
    }

    @Override
    public @Nullable Object getPrincipal() {
        return user;
    }

    public SecurityUser getUser(){
        return user;
    }
}
