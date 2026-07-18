package com.techforge.identityprovider.util;

import com.techforge.identityprovider.configuration.system.SystemAuthenticationToken;
import com.techforge.identityprovider.dto.SecurityUser;
import com.techforge.identityprovider.exception.AppException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class Utils {

    private final SecurityContextRepository repository;

    private final RequestCache requestCache;


    public Utils(SecurityContextRepository repository, RequestCache requestCache) {
        this.repository = repository;
        this.requestCache = requestCache;
    }

    public void storeContext(Authentication authentication, HttpServletRequest request, HttpServletResponse response){
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(authentication);
        repository.saveContext(context,request,response);
    }

    public String getRedirectUrl(HttpServletRequest request, HttpServletResponse response){
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        if(Optional.ofNullable(savedRequest).isPresent()){
            return savedRequest.getRedirectUrl();
        }
        throw new AppException("Error occurred");
    }

    public Authentication getSystemAuthenticationToken(SecurityUser user){
        List<GrantedAuthority> authorities = new ArrayList<>(user.getAuthorities());
        authorities.remove(new SimpleGrantedAuthority("TOTP_PENDING"));
        return new SystemAuthenticationToken(user, authorities);
    }
}
