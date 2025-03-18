package com.egg.electricidad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.egg.electricidad.entity.Factory;

@Repository
public interface FactoryRepository extends JpaRepository<Factory, String>{

}
