package com.imam.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	
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
	public String products() {
		return "product";
	}
	
	@GetMapping("/product")
	public String product() {
		return "view_product";
	}

}
