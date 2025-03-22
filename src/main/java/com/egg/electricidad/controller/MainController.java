package com.egg.electricidad.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/")
public class MainController {

    @GetMapping("/")
    public String index() {
        return "index.html";
    }

    @GetMapping("/inicio")
    public String home(HttpSession session) {
        return "inicio.html";
    }

    @GetMapping("/redirect-home")
    public RedirectView redirectHome(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails != null) {
            return new RedirectView("/inicio");
        }
        return new RedirectView("/");
    }
}
