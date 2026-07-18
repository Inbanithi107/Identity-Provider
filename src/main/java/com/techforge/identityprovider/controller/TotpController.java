package com.techforge.identityprovider.controller;

import com.techforge.identityprovider.configuration.totp.TotpAuthenticationToken;
import com.techforge.identityprovider.dto.QrResponse;
import com.techforge.identityprovider.dto.SecurityUser;
import com.techforge.identityprovider.dto.TotpAuthRequest;
import com.techforge.identityprovider.entity.User;
import com.techforge.identityprovider.exception.AppException;
import com.techforge.identityprovider.service.TotpService;
import com.techforge.identityprovider.service.UserService;
import com.techforge.identityprovider.util.Utils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/totp")
public class TotpController {

    private final TotpService totpService;

    private final RequestCache requestCache;

    private final AuthenticationManager manager;

    private final UserService userService;

    private final Utils utils;

    public TotpController(TotpService totpService, RequestCache requestCache, AuthenticationManager manager, UserService userService, Utils storage){
        this.totpService = totpService;
        this.requestCache = requestCache;
        this.manager = manager;
        this.userService = userService;
        this.utils = storage;
    }

    @GetMapping("/")
    public String totp(@AuthenticationPrincipal SecurityUser user, HttpServletRequest request, HttpServletResponse response){
        boolean isMfaEnabled = user.getUser().isMfaEnabled();
        if(isMfaEnabled){
            return "totpVerify";
        }
        SavedRequest savedRequest = requestCache.getRequest(request,response);
        if(Optional.ofNullable(savedRequest).isPresent()){
            String redirectUrl = savedRequest.getRedirectUrl();
            return "redirect:"+redirectUrl;
        }
        return "index";
    }

    @GetMapping("/setup")
    public String totpSetup(){
        return "totpSetup";
    }

    @PostMapping("/verify")
    @ResponseBody
    public Map<String,Object> verify(@RequestBody TotpAuthRequest totp, @AuthenticationPrincipal SecurityUser securityUser, HttpServletRequest servletRequest, HttpServletResponse servletResponse){
        User user = userService.getUserById(securityUser.getUser().getId());
        securityUser.setUser(user);
        Authentication authentication = manager.authenticate(new TotpAuthenticationToken(securityUser, totp.getCode()));
        if(authentication.isAuthenticated()){
            utils.storeContext(utils.getSystemAuthenticationToken(securityUser), servletRequest, servletResponse);
            return Map.of("redirect", utils.getRedirectUrl(servletRequest, servletResponse));
        }
        throw new AppException("Totp authentication failed");
    }

    @PostMapping("/qr")
    @ResponseBody
    public QrResponse generateQr(Authentication authentication, HttpServletResponse response) throws IOException {
        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
        User user = userService.getUserById(securityUser.getUser().getId());
        if(user.isMfaEnabled()){
            throw new AppException("Authentication flow failed");
        }
        String secret = totpService.generateSecret();
        user.setSecret(secret);
        user.setMfaEnabled(true);
        userService.updateUser(user);
        String qr = totpService.generateQrString(securityUser.getUsername(), secret);
        return new QrResponse(qr);
    }

    @GetMapping("/passcode")
    public String passcode(){
        return "passcode";
    }

    @GetMapping("/skip-totp")
    public String skipTotp(@AuthenticationPrincipal SecurityUser securityUser, HttpServletRequest request, HttpServletResponse response){
        User user = userService.getUserById(securityUser.getUser().getId());
        securityUser.setUser(user);
        utils.storeContext(utils.getSystemAuthenticationToken(securityUser), request, response);
        return "redirect:"+utils.getRedirectUrl(request,response);
    }

}
