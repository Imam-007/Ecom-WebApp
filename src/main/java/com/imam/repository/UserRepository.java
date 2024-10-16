package com.imam.repository;

import com.imam.model.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserDetails,Integer> {

}
