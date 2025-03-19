package com.egg.electricidad.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.egg.electricidad.entity.User;

import jakarta.servlet.http.HttpSession;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ModelAttribute("user")
    public User addUserToModel(HttpSession session) {
        if (session.getAttribute("usersession") == null) {
            return null;
        }else{
            return (User) session.getAttribute("usersession");
        }
    }
}