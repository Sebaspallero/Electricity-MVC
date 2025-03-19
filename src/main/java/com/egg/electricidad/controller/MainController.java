package com.egg.electricidad.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
