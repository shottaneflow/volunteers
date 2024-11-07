package com.volonteers.service;

import com.volonteers.model.Volunteer;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    Volunteer findByUsername(String username);
    void addUser(Volunteer user);
    void activateUser(String code);
}
