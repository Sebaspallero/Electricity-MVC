package com.egg.electricidad.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.egg.electricidad.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String>{

    @Query("SELECT u FROM User u WHERE u.email = :email")
    public User findByEmail(@Param("email") String email);

    @Query("SELECT u.email FROM User u")
    List<String> findAllEmails();

}
