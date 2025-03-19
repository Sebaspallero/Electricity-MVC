package com.egg.electricidad.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.egg.electricidad.entity.User;
import com.egg.electricidad.exceptions.InvalidArgumentException;
import com.egg.electricidad.repository.UserRepository;
import com.egg.electricidad.util.Role;

import jakarta.servlet.http.HttpSession;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Service
public class UserService implements UserDetailsService{

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void createUser(String name, String lastName, String email, String password, String password2) {
        validate(name, lastName, email, password, password2);
        validateUniqueEmail(email);

        User user = new User();

        user.setName(name);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(new BCryptPasswordEncoder().encode(password));
        user.setRole(Role.USER);
        
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User findById(String id) {
        
        Optional<User> response = userRepository.findById(id);
        if (response.isPresent()) {
            return response.get();
        }else{
            throw new InvalidArgumentException("User not found with id: " + id);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);

        if (user != null) {

            List<GrantedAuthority> permits = new ArrayList<>();
            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + user.getRole().toString());
            permits.add(p);

            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession(true);
            session.setAttribute("usersession", user); 

            return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),  permits);
            
        } else {
            return null;
        }
    }


    private void validate(String name, String lastName, String email, String password, String password2) {
        if (name.isEmpty() || name == null) {
            throw new InvalidArgumentException("the name cannot be null or empty");
        }
        if(lastName.isEmpty() || lastName == null){
            throw new InvalidArgumentException("the last name cannot be null or empty");
        }
        if (email.isEmpty() || email == null) {
            throw new InvalidArgumentException("the email cannot be null or empty");
        }
        if (password.isEmpty() || password == null || password.length() <= 5) {
            throw new InvalidArgumentException("the password cannot be null or empty and must have more than 5 characters");
        }
        if (!password.equals(password2)) {
            throw new InvalidArgumentException("the passwords do not match");
        }
    }

    public boolean validateUniqueEmail(String email) {
        List<String> emails = userRepository.findAllEmails();
        if (emails.contains(email)) {
            throw new InvalidArgumentException("the email is already registered");
        }
        return true;
    }

    
}
