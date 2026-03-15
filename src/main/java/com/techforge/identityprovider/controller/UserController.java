package com.techforge.identityprovider.controller;

import com.techforge.identityprovider.dto.RegisterUserRequest;
import com.techforge.identityprovider.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@ModelAttribute RegisterUserRequest request){
        userService.register(request.getEmail(), request.getPassword());
        return new ResponseEntity<>("user registered successfully", HttpStatus.OK);
    }

}
