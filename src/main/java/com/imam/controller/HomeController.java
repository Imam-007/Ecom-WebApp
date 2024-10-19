package com.imam.controller;

import com.imam.model.Category;
import com.imam.model.Product;
import com.imam.model.UserDetails;
import com.imam.service.CategoryService;
import com.imam.service.ProductService;
import com.imam.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

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
    public String show() {
        return "index";
    }

    @GetMapping("/signin")
    public String login() {
        return "login";
    }

    @GetMapping("/signUp")
    public String register() {
        return "register";
    }

    @GetMapping("/products")
    public String products(Model model, @RequestParam(value = "category", defaultValue = "") String category) {
        System.out.println("category" + category);
        List<Category> categories = categoryService.getAllActiveCategory();
        List<Product> products = productService.getAllActiveProducts(category);
        System.out.println(products);
        model.addAttribute("categories", categories);
        model.addAttribute("products", products);
        model.addAttribute("paramValue", category);
        return "product";
    }

    @GetMapping("/product/{id}")
    public String product(@PathVariable int id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "view_product";
    }

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute UserDetails userDetails, @RequestParam("img") MultipartFile file, HttpSession session) throws IOException {

        String imageName = file.isEmpty() ? "default.jpg" : file.getOriginalFilename();
        userDetails.setProfileImage(imageName);
        UserDetails saveUserDetails = userService.saveUser(userDetails);

        if (!ObjectUtils.isEmpty(saveUserDetails)) {
            if (!file.isEmpty()) {
                File saveFile = new ClassPathResource("static/images").getFile();
                File profileImgDir = new File(saveFile, "profile_img");

                if (!profileImgDir.exists()) {
                    profileImgDir.mkdirs();
                }

                Path path = Paths.get(profileImgDir.getAbsolutePath() + File.separator + file.getOriginalFilename());
                System.out.println("hello");
                System.out.println(path);
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            }
            return "login";
        } else {
            session.setAttribute("errorMsg", "Something went wrong on server");
        }
        return "register";
    }

}
