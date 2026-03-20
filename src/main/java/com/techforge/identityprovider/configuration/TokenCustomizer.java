package com.techforge.identityprovider.configuration;

import com.techforge.identityprovider.dto.SecurityUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.oidc.endpoint.OidcParameterNames;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.stereotype.Component;

@Component
public class TokenCustomizer implements OAuth2TokenCustomizer<JwtEncodingContext> {
    @Override
    public void customize(JwtEncodingContext context) {
        SecurityUser user = (SecurityUser) context.getPrincipal().getPrincipal();
        if(OidcParameterNames.ID_TOKEN.equals(context.getTokenType().getValue())){
            context.getClaims().claim("name", user.getName());
        }
        if(OAuth2TokenType.ACCESS_TOKEN.equals(context.getTokenType())){
            context.getClaims().claim("id", user.getUser().getId().toString());
            context.getClaims().claim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList());
        }
    }
}
