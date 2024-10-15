package com.imam.controller;

import com.imam.model.Category;
import com.imam.model.Product;
import com.imam.service.CategoryService;
import com.imam.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private ProductService productService;
	
	@GetMapping("/")
	public String show() {
		return "index";
	}
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@GetMapping("/signUp")
	public String register() {
		return "register";
	}
	
	@GetMapping("/products")
	public String products(Model model) {
		List<Category> categories= categoryService.getAllActiveCategory();
		List<Product> products= productService.getAllActiveProducts();
		model.addAttribute("categories",categories);
		model.addAttribute("products",products);
		return "product";
	}
	
	@GetMapping("/product")
	public String product() {
		return "view_product";
	}

}
