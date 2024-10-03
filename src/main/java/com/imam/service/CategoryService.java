package com.imam.service;

import java.util.List;

import com.imam.model.Category;

public interface CategoryService {
	
	public Category saveCategory(Category category);
	
	public boolean existCategory(String name);
	
	public List<Category> getAllCategory();

}
