package com.egg.electricidad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager;

import com.egg.electricidad.exceptions.InvalidArgumentException;
import com.egg.electricidad.service.UserService;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

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
            authenticateUser(email, password);
            modelo.put("succes", "Successfully registered user");
            return "redirect:/inicio";
        } catch (InvalidArgumentException e) {
            modelo.put("error", e.getMessage());
            return "redirect:/auth/register";
        }
    }

    private void authenticateUser(String email, String password) {
        try {
            // Cargar los detalles del usuario desde la base de datos
            UserDetails userDetails = userService.loadUserByUsername(email);
            
            // Crear el token de autenticación usando los detalles del usuario
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails, password, userDetails.getAuthorities());
    
            // Obtener el AuthenticationManager y autenticar al usuario
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
    
            // Establecer la autenticación en el contexto de seguridad
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (UsernameNotFoundException e) {
            throw new RuntimeException("Error during authentication", e);
        }
    }

}
