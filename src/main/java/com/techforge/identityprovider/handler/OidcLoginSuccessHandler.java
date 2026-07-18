package com.techforge.identityprovider.handler;

import com.techforge.identityprovider.dto.SecurityUser;
import com.techforge.identityprovider.util.Utils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

@Component
public class OidcLoginSuccessHandler implements AuthenticationSuccessHandler {

    private final Utils utils;

    public OidcLoginSuccessHandler(Utils utils) {
        this.utils = utils;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        utils.storeContext(utils.getSystemAuthenticationToken((SecurityUser) Objects.requireNonNull(authentication.getPrincipal())), request, response);
        response.sendRedirect(utils.getRedirectUrl(request,response));
    }
}
