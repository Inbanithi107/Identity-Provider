package com.techforge.identityprovider.controller;

import com.techforge.identityprovider.exception.AppException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApplicationErrorHandler {

    @ExceptionHandler(AppException.class)
    public String handleException(AppException e, Model model){
        model.addAttribute("code", 500);
        model.addAttribute("message", e.getMessage());
        return "error";
    }

}
