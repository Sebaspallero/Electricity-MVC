package com.egg.electricidad.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.egg.electricidad.entity.Factory;
import com.egg.electricidad.exceptions.InvalidArgumentException;
import com.egg.electricidad.repository.FactoryRepository;

@Service
public class FactoryService {

    @Autowired
    private FactoryRepository factoryRepository;

    @Transactional
    public void createFactory(String name) {

        Factory factory = new Factory();

        factory.setName(name);

        factoryRepository.save(factory);
    }

    @Transactional(readOnly = true)
    public Factory findById(String id) {

        Optional<Factory> response = factoryRepository.findById(id);
        if (response.isPresent()) {
            return response.get();
        } else {
            throw new InvalidArgumentException("Factory not found with id: " + id);
        }
    }

    @Transactional(readOnly = true)
    public List<Factory> findAll() {
        List<Factory> factory = new ArrayList<>();
        factory = factoryRepository.findAll();
        return factory;
    }

    @Transactional
    public void updateFactory(String name, String id) {

        Optional<Factory> response = factoryRepository.findById(id);

        if (response.isPresent()) {
            Factory factory = response.get();

            factory.setName(name);
            factoryRepository.save(factory);

        } else {
            throw new InvalidArgumentException("Factory not found with id: " + id);
        }
    }

    @Transactional
    public void deleteFactory(String id) {
        Optional<Factory> response = factoryRepository.findById(id);
        if (response.isPresent()) {
            factoryRepository.deleteById(id);
        } else {
            throw new InvalidArgumentException("Factory not found with id: " + id);
        }
    }
}
