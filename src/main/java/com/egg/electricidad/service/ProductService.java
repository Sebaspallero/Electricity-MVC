package com.egg.electricidad.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.egg.electricidad.entity.Factory;
import com.egg.electricidad.entity.Product;
import com.egg.electricidad.exceptions.InvalidArgumentException;
import com.egg.electricidad.repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private FactoryService factoryService;

    @Transactional
    public void createProduct(String name, String description, String factoryId){

        Product product = new Product();
        Factory factory = factoryService.findById(factoryId);

        product.setName(name);
        product.setProductDescription(description);
        product.setFactory(factory);

        productRepository.save(product);
    }

    @Transactional(readOnly = true)
    public Product findById(String id){
        
        Optional<Product> response = productRepository.findById(id);
        if (response.isPresent()) {
            return response.get();
        }else{
            throw new InvalidArgumentException("Product not found with id: " + id);
        }
    }

   
    @Transactional(readOnly = true)
    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();
        products = productRepository.findAll();
        return products;
    }

    @Transactional
    public void updateProduct(String name, String description, String productId, String factoryId) {
          
        Factory factory = factoryService.findById(factoryId);        
        Product product = findById(productId);

        product.setName(name);
        product.setProductDescription(description);
        product.setFactory(factory);

        productRepository.save(product);
    }

    @Transactional
    public void deleteProduct(String id){
        Optional<Product> response = productRepository.findById(id);
        if (response.isPresent()) {
            productRepository.deleteById(id);
        }else{
            throw new InvalidArgumentException("Product not found with id: " + id);
        }
    }
}
