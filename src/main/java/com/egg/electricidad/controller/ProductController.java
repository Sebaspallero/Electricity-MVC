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
import com.egg.electricidad.entity.Product;
import com.egg.electricidad.exceptions.InvalidArgumentException;
import com.egg.electricidad.service.ProductService;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @RequestMapping("/list")
    public String list(ModelMap model) {
        List<Product> products = productService.findAll();
        model.addAttribute("products", products);
        return "productList.html";
    }

    @GetMapping("product/{id}")
    public String getById(@RequestParam String id, ModelMap model) {
        try {
            Product product = productService.findById(id);
            model.addAttribute("product", product);
            return "product.html";

        } catch (Exception e) {
            model.put("error", e.getMessage());
            return "redirect:/product/list";
        }
    }

    @GetMapping("/register-product")
    public String registrar() {
        return "productForm.html";
    }

    @PostMapping("/register-product")
    public String registro(@RequestParam String name, @RequestParam String productDescription, @RequestParam Factory factory, ModelMap modelo) {
        try {
            productService.createProduct(name, productDescription, factory);
            modelo.put("succes", "Product successfully registered");
            return "redirect:/inicio";

        } catch (InvalidArgumentException ex) {
            modelo.put("error", ex.getMessage());
            return "productForm.html";
        }
    }
}
