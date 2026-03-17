package com.techforge.identityprovider.controller;

import com.techforge.identityprovider.dto.SecurityUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {

    @GetMapping("/home")
    public String hello(@AuthenticationPrincipal SecurityUser user){
        return "hello "+user.getName();
    }

}
