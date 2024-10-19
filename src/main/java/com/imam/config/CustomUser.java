package com.imam.config;

import com.imam.model.UserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.Collection;

public class CustomUser implements org.springframework.security.core.userdetails.UserDetails {

    private UserDetails userDetails;

    public CustomUser(UserDetails userDetails) {
        super();
        this.userDetails = userDetails;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority=new SimpleGrantedAuthority(userDetails.getRole());
        return Arrays.asList(authority);
    }

    @Override
    public String getPassword() {
        return userDetails.getPassword();
    }

    @Override
    public String getUsername() {
        return userDetails.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return userDetails.getIsEnable();
    }
}
