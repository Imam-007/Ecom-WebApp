package com.imam.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;

import com.imam.model.UserDetails;
import com.imam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.imam.model.Category;
import com.imam.model.Product;
import com.imam.service.CategoryService;
import com.imam.service.ProductService;

import java.nio.file.Files;
import java.nio.file.Path;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {

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
    public String index() {

        return "admin/index";
    }

    @GetMapping("/loadAddProduct")
    public String loadAddProduct(Model model) {
        List<Category> categories = categoryService.getAllCategory();
        model.addAttribute("categories", categories);

        return "admin/add_product";
    }

    @GetMapping("/category")
    public String category(Model model) {
        model.addAttribute("categorys", categoryService.getAllCategory());

        return "admin/category";
    }

    @PostMapping("/saveCategory")
    public String saveCategory(@ModelAttribute Category category, @RequestParam("file") MultipartFile file,
                               HttpSession session) throws IOException {

        String imageName = file != null ? file.getOriginalFilename() : "default.jpg";
        category.setImageName(imageName);
        System.out.println(category.getIsActive());
        category.setIsActive(category.getIsActive());

        boolean existCategory = categoryService.existCategory(category.getName());

        if (existCategory) {
            session.setAttribute("errorMsg", "category Name already Exists");
        } else {
            Category saveCategory = categoryService.saveCategory(category);

            if (ObjectUtils.isEmpty(saveCategory)) {
                session.setAttribute("errorMsg", "Not Saved ! Internal Server Error");
            } else {
                File saveFile = new ClassPathResource("static/images").getFile();
                Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + "category_img" + File.separator
                        + file.getOriginalFilename());

                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                session.setAttribute("successMsg", "Saved SuccessFully");
            }
        }

        return "redirect:/admin/category";
    }

    @GetMapping("/deleteCategory/{id}")
    public String deleteCategory(@PathVariable int id, HttpSession session) {
        boolean deleteCategory = categoryService.deleteCategory(id);
        if (deleteCategory) {
            session.setAttribute("successMsg", "Category deleted successfully");
        } else {
            session.setAttribute("errorMsg", "Something went wrong on server");
        }
        return "redirect:/admin/category";
    }

    @GetMapping("/loadEditCategory/{id}")
    public String loadEditCategory(@PathVariable int id, Model model) {
        model.addAttribute("category", categoryService.getCategoryById(id));
        return "admin/edit_category";
    }

    @PostMapping("/updateCategory")
    public String updateCategory(@ModelAttribute Category category, @RequestParam("file") MultipartFile file,
                                 HttpSession session) throws IOException {
        Category oldCategory = categoryService.getCategoryById(category.getId());
        String imageName = file.isEmpty() ? oldCategory.getImageName() : file.getOriginalFilename();
        if (!ObjectUtils.isEmpty(oldCategory)) {
            oldCategory.setName(category.getName());
            oldCategory.setIsActive(category.getIsActive());
            oldCategory.setImageName(imageName);
        }
        Category UpdateCategory = categoryService.saveCategory(oldCategory);

        if (!ObjectUtils.isEmpty(UpdateCategory)) {
            if (!file.isEmpty()) {
                File saveFile = new ClassPathResource("static/images").getFile();
                Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + "category_img" + File.separator
                        + file.getOriginalFilename());

                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            }
            session.setAttribute("successMsg", "Category Update SuccessFully");
        } else {
            session.setAttribute("errorMsg", "Something went wrong on server");
        }
        return "redirect:/admin/category";
    }

    @PostMapping("/saveProduct")
    public String saveProduct(@ModelAttribute Product product, HttpSession session,
                              @RequestParam("file") MultipartFile image) throws IOException {

        String imageName = image.isEmpty() ? "default.png" : image.getOriginalFilename();
        product.setImage(imageName);
        product.setDiscount(0);
        product.setDiscountPrice(product.getPrice());
        product.setIsActive(product.getIsActive());

        Product saveProduct = productService.saveProduct(product);

        if (!ObjectUtils.isEmpty(saveProduct)) {
            File saveFile = new ClassPathResource("static/images").getFile();
            Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + "product_img" + File.separator
                    + image.getOriginalFilename());

            System.out.println(path);
            Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

            session.setAttribute("successMsg", "Product saved SuccessFully");
        } else {
            session.setAttribute("errorMsg", "Something went wrong on server");
        }
        return "redirect:/admin/products";
    }

    @GetMapping("/products")
    public String loadViewProduct(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "admin/products";
    }

    @GetMapping("/deleteProduct/{id}")
    public String deleteProduct(@PathVariable int id, HttpSession session) {

        Boolean deleteProduct = productService.deleteProduct(id);

        if (deleteProduct) {
            session.setAttribute("successMsg", "Product saved SuccessFully");
        } else {
            session.setAttribute("errorMsg", "Something went wrong on server");
        }
        return "redirect:/admin/products";
    }

    @GetMapping("/editProduct/{id}")
    public String editProduct(@PathVariable int id, Model model) {
        model.addAttribute("product", productService.getProductById(id));
        model.addAttribute("categories", categoryService.getAllCategory());
        return "admin/edit_product";
    }

    @PostMapping("/updateProduct")
    public String updateProduct(@ModelAttribute Product product, @RequestParam MultipartFile file, HttpSession session) {

        if (product.getDiscount() < 0 || product.getDiscount() > 100) {
            session.setAttribute("errorMsg", "Invalid Discount");
            return "redirect:/admin/editProduct/" + product.getId();
        } else {
            Product updateProduct = productService.updateProduct(product, file);
            if (!ObjectUtils.isEmpty(updateProduct)) {
                session.setAttribute("successMsg", "Product updated SuccessFully");
            } else {
                session.setAttribute("errorMsg", "Something went wrong on server");
                return "redirect:/admin/editProduct/" + product.getId();
            }
        }
        return "redirect:/admin/products";
    }

}
