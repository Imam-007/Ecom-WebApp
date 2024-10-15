package com.imam.controller;

import com.imam.model.Category;
import com.imam.model.Product;
import com.imam.service.CategoryService;
import com.imam.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

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
	public String products(Model model, @RequestParam(value = "category", defaultValue = "") String category) {
		System.out.println("category"+category);
		List<Category> categories= categoryService.getAllActiveCategory();
		List<Product> products= productService.getAllActiveProducts(category);
		System.out.println(products);
		model.addAttribute("categories",categories);
		model.addAttribute("products",products);
		model.addAttribute("paramValue",category);
		return "product";
	}
	
	@GetMapping("/product/{id}")
	public String product(@PathVariable int id, Model model) {
		Product product=productService.getProductById(id);
		model.addAttribute("product",product);
		return "view_product";
	}

}
