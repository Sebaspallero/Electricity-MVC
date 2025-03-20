package com.egg.electricidad.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.egg.electricidad.entity.Factory;
import com.egg.electricidad.entity.Product;
import com.egg.electricidad.exceptions.InvalidArgumentException;
import com.egg.electricidad.service.FactoryService;
import com.egg.electricidad.service.ProductService;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private FactoryService factoryService;

    @RequestMapping("/list")
    public String list(ModelMap model) {
        List<Product> products = productService.findAll();
        model.addAttribute("products", products);
        return "productList.html";
    }

    @GetMapping("update/{id}")
    public String getById(@PathVariable String id, ModelMap model) {
        try {
            Product product = productService.findById(id);
            model.addAttribute("product", product);
            return "product.html";

        } catch (Exception e) {
            model.put("error", e.getMessage());
            return "redirect:/product/list";
        }
    }

    @PostMapping("delete/{id}")
    public String delete(@PathVariable String id, ModelMap model) {
        try {
            productService.deleteProduct(id);
            model.put("succes", "Product successfully deleted");
            return "redirect:/product/list";
        } catch (Exception e) {
            model.put("error", e.getMessage());
            return "redirect:/product/list";
        }
    }

    @GetMapping("/register-product")
    public String registrar(ModelMap model) {
        List<Factory> factories = factoryService.findAll();
        model.addAttribute("factories", factories);
        return "productForm.html";
    }

    @PostMapping("/register-product")
    public String registro(@RequestParam String name, @RequestParam String productDescription, @RequestParam String factoryId, ModelMap modelo) {
        try {
            productService.createProduct(name, productDescription, factoryId);
            modelo.put("succes", "Product successfully registered");
            return "redirect:/inicio";
            
        } catch (InvalidArgumentException ex) {
            modelo.put("error", ex.getMessage());
            return "productForm.html";
        }
    }
}
