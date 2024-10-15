package com.imam.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.imam.model.Category;
import com.imam.repository.CategoryRepository;
import com.imam.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category saveCategory(Category category) {
        // TODO Auto-generated method stub
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> getAllCategory() {
        // TODO Auto-generated method stub
        return categoryRepository.findAll();
    }

    @Override
    public boolean existCategory(String name) {
        // TODO Auto-generated method stub
        return categoryRepository.existsByName(name);
    }

    @Override
    public boolean deleteCategory(int id) {
        // TODO Auto-generated method stub
        Category category = categoryRepository.findById(id).orElse(null);

        if (!ObjectUtils.isEmpty(category)) {
            categoryRepository.delete(category);
            return true;
        }
        return false;
    }

    @Override
    public Category getCategoryById(int id) {
        // TODO Auto-generated method stub
        Category category = categoryRepository.findById(id).orElse(null);
        return category;
    }

    @Override
    public List<Category> getAllActiveCategory() {
        return categoryRepository.findByIsActiveTrue();
    }

}
