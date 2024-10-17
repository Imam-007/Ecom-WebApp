package com.imam.controller;

import com.imam.model.Category;
import com.imam.model.UserDetails;
import com.imam.service.CategoryService;
import com.imam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @ModelAttribute
    public void getUserDetails(Principal principal, Model model){

        if(principal!=null){
            String email=principal.getName();
            UserDetails userDetails=userService.getUserByEmail(email);
            model.addAttribute("user",userDetails);
        }

        List<Category> allActiveCategory=categoryService.getAllActiveCategory();
        model.addAttribute("categories",allActiveCategory);
    }

    @GetMapping("/")
    public String home(){

        return "user/home";
    }
}
