package com.techforge.identityprovider.controller;

import com.techforge.identityprovider.dto.RegisterUserRequest;
import com.techforge.identityprovider.service.UserService;
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

import java.util.Optional;

@Controller
public class UserController {

    private final UserService userService;

    private final SecurityContextRepository securityContextRepository;

    public UserController(UserService userService, SecurityContextRepository securityContextRepository) {
        this.userService = userService;
        this.securityContextRepository = securityContextRepository;
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute RegisterUserRequest request, HttpServletRequest httpRequest, HttpServletResponse httpResponse){
        Authentication authentication = userService.register(request.getEmail(), request.getPassword(), request.getName());
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
        securityContextRepository.saveContext(context, httpRequest, httpResponse);
        RequestCache requestCache = new HttpSessionRequestCache();
        SavedRequest savedRequest = requestCache.getRequest(httpRequest, httpResponse);
        if(Optional.ofNullable(savedRequest).isPresent()){
            String redirectUri = savedRequest.getRedirectUrl();
            return "redirect:"+redirectUri;
        }
        return null;
    }

}
