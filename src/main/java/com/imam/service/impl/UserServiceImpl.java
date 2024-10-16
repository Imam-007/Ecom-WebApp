package com.imam.service.impl;

import com.imam.model.UserDetails;
import com.imam.repository.UserRepository;
import com.imam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails saveUser(UserDetails user) {
        return userRepository.save(user);
    }
}
