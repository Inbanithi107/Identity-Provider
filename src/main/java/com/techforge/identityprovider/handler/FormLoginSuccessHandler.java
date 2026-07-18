package com.techforge.identityprovider.handler;

import com.techforge.identityprovider.configuration.totp.TotpAuthenticationToken;
import com.techforge.identityprovider.dto.SecurityUser;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class FormLoginSuccessHandler implements AuthenticationSuccessHandler {

    private final String MFA_URL = "/totp/passcode";

    private final String MFA_SETUP_URL = "/totp/setup";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        SecurityUser user = (SecurityUser) authentication.getPrincipal();
        boolean isMfaEnabled = user.getUser().isMfaEnabled();
        if(isMfaEnabled){
            response.sendRedirect(MFA_URL);
        }else{
            response.sendRedirect(MFA_SETUP_URL);
        }
    }
}
