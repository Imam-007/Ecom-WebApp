package com.imam.service.impl;

import com.imam.model.UserDetails;
import com.imam.repository.UserRepository;
import com.imam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails saveUser(UserDetails userDetails) {

        userDetails.setRole("ROLE_USER");
        userDetails.setIsEnable(true);
        String encodedPassword=passwordEncoder.encode(userDetails.getPassword());
        userDetails.setPassword(encodedPassword);
        return userRepository.save(userDetails);
    }

    @Override
    public UserDetails getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
