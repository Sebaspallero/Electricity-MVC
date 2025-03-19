package com.egg.electricidad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.egg.electricidad.exceptions.InvalidArgumentException;
import com.egg.electricidad.service.UserService;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;
    
    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, ModelMap modelo ) {
        if (error != null) {
            modelo.put("error", "User or password incorrect");        
        }
        return "login.html";
    }
    
    @GetMapping("/register")
    public String register(){
        return "register.html";
    }
    
    @PostMapping("/saveUser")
    public String saveUser(@RequestParam String name, @RequestParam String lastName, @RequestParam String email,@RequestParam String password, @RequestParam String password2, ModelMap modelo ){
        try {
            userService.createUser(name, lastName, email, password, password2);
            modelo.put("succes", "Successfully registered user");
            return "redirect:/";
        } catch (InvalidArgumentException e) {
            modelo.put("error", e.getMessage());
            return "redirect:/auth/register";
        }
    }

}
