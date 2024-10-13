package com.imam.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.imam.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>{

}
