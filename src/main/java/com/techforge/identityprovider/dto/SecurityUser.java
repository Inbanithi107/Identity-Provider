package com.techforge.identityprovider.dto;

import com.techforge.identityprovider.entity.User;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.FactorGrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class SecurityUser implements UserDetails, OidcUser {

    private User user;

    private OidcUser oidcUser;

    List<GrantedAuthority> authorities = new ArrayList<>();


    @Override
    public Map<String, Object> getAttributes() {
        return oidcUser.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(!authorities.isEmpty()){
            return authorities;
        }
        for(Role role : user.getRoles()){
            authorities.add(new SimpleGrantedAuthority(role.name()));
        }
        if(oidcUser!=null){
            authorities.addAll(oidcUser.getAuthorities());
        } else {
            authorities.add(new SimpleGrantedAuthority("TOTP_PENDING"));
        }
        FactorGrantedAuthority authority = FactorGrantedAuthority.fromFactor("USER");
        authorities.add(authority);
        return authorities;
    }

    public void addAuthority(String name){
        authorities.add(new SimpleGrantedAuthority(name));
    }

    @Override
    public @Nullable String getPassword() {
        return user.getPasswordHash();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    public String getName(){
        if(Optional.ofNullable(user.getName()).isEmpty()){
            return Objects.requireNonNull(getAttribute("name"));
        }else{
            return user.getName();
        }
    }

    public SecurityUser setUser(User user) {
        this.user = user;
        return this;
    }

    public SecurityUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public SecurityUser(User user, OidcUser oidcUser) {
        this.user = user;
        this.oidcUser = oidcUser;
    }

    @Override
    public Map<String, Object> getClaims() {
        return oidcUser.getClaims();
    }

    @Override
    public OidcUserInfo getUserInfo() {
        return oidcUser.getUserInfo();
    }

    @Override
    public OidcIdToken getIdToken() {
        return oidcUser.getIdToken();
    }
}
