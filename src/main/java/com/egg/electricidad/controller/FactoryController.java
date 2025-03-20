package com.egg.electricidad.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.egg.electricidad.entity.Factory;
import com.egg.electricidad.exceptions.InvalidArgumentException;
import com.egg.electricidad.service.FactoryService;

@Controller
@RequestMapping("/factory")
public class FactoryController {

    @Autowired
    private FactoryService factoryService;

    @RequestMapping("/list")
    public String list(ModelMap model) {
        List<Factory> factories = factoryService.findAll();
        model.addAttribute("factories", factories);
        return "factoryList.html";
    }

    @GetMapping("factory/{id}")
    public String getById(@RequestParam String id, ModelMap model) {
        try {
            Factory factory = factoryService.findById(id);
            model.addAttribute("factory", factory);
            return "factory.html";

        } catch (Exception e) {
            model.put("error", e.getMessage());
            return "redirect:/factory/list";
        }
    }

    @GetMapping("/register-factory")
    public String registerFactory() {
        return "factoryForm.html";
    }

    @PostMapping("/register-factory")
    public String registro(@RequestParam String name, ModelMap model) {
        try {
            factoryService.createFactory(name);
            model.put("succes", "Factory successfully registered");
            return "redirect:/inicio";

        } catch (InvalidArgumentException ex) {
            model.put("error", ex.getMessage());
            return "factoryForm.html";
        }
    }
}
