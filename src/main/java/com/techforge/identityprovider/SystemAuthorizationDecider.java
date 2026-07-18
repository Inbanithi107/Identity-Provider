package com.techforge.identityprovider;


import com.techforge.identityprovider.configuration.system.SystemAuthenticationToken;
import org.jspecify.annotations.Nullable;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.authorization.AuthorizationResult;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;

import java.util.function.Supplier;

public class SystemAuthorizationDecider implements AuthorizationManager<RequestAuthorizationContext> {
    @Override
    public @Nullable AuthorizationResult authorize(Supplier<? extends @Nullable Authentication> authentication, RequestAuthorizationContext context) {
        Authentication auth = authentication.get();
        boolean granted = auth instanceof SystemAuthenticationToken;
        return new AuthorizationDecision(granted);
    }
}
