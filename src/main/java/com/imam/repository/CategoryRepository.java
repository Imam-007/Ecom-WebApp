package com.imam.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.imam.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
	
	public boolean existsByName(String name);

}
