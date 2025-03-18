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

    @Transactional
    public void createProduct(String name, String description, Factory factoryId){

        Product product = new Product();

        product.setName(name);
        product.setProductDescription(description);
        product.setFactory(factoryId);

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
    public void updateProduct(String name, String description, String id){
          
        Optional<Product> response = productRepository.findById(id);

        if (response.isPresent()) {
            Product product = response.get();
           
            product.setName(name);
            product.setProductDescription(description);
            productRepository.save(product);
        }else{
            throw new InvalidArgumentException("Product not found with id: " + id);
        }
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
