package com.techforge.identityprovider.configuration.system;

import com.techforge.identityprovider.dto.SecurityUser;
import org.jspecify.annotations.Nullable;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class SystemAuthenticationToken extends AbstractAuthenticationToken {

    private final SecurityUser user;

    public SystemAuthenticationToken(Authentication authentication, SecurityUser user){
        super(authentication.getAuthorities());
        this.user = user;
    }

    public SystemAuthenticationToken(SecurityUser user, Collection<? extends GrantedAuthority> authorities){
        super(authorities);
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
}
