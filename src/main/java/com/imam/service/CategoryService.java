package com.imam.service;

import java.util.List;

import com.imam.model.Category;

public interface CategoryService {
	
	public Category saveCategory(Category category);
	
	public boolean existCategory(String name);
	
	public List<Category> getAllCategory();
	
	public boolean deleteCategory(int id);
	
	public Category getCategoryById(int id);

	public List<Category> getAllActiveCategory();

}
