package com.imam.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.imam.model.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    public List<Product> findByIsActiveTrue();

    public List<Product> findByCategory(String category);
}
