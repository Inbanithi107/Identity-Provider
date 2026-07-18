package com.techforge.identityprovider.controller;

import com.techforge.identityprovider.dto.RegisterUserRequest;
import com.techforge.identityprovider.handler.FormLoginSuccessHandler;
import com.techforge.identityprovider.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.util.Optional;

@Controller
public class UserController {

    private final UserService userService;

    private final SecurityContextRepository securityContextRepository;

    private final FormLoginSuccessHandler handler;
    private final RequestCache requestCache = new HttpSessionRequestCache();

    public UserController(UserService userService, SecurityContextRepository securityContextRepository, FormLoginSuccessHandler handler) {
        this.userService = userService;
        this.securityContextRepository = securityContextRepository;
        this.handler = handler;
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute RegisterUserRequest request, HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws ServletException, IOException {
        Authentication authentication = userService.register(request.getEmail(), request.getPassword(), request.getName());
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
        securityContextRepository.saveContext(context, httpRequest, httpResponse);
        handler.onAuthenticationSuccess(httpRequest, httpResponse, authentication);
        return null;
    }

}
