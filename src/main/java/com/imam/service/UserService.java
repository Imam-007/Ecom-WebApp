package com.imam.service;

import com.imam.model.UserDetails;

public interface UserService {

    public UserDetails saveUser(UserDetails userDetails);

    public UserDetails getUserByEmail(String email);
}
