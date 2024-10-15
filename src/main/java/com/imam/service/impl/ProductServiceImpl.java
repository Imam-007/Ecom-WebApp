package com.imam.service.impl;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import com.imam.model.Product;
import com.imam.repository.ProductRepository;
import com.imam.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product saveProduct(Product product) {
        // TODO Auto-generated method stub
        return productRepository.save(product);
    }

    @Override
    public List<Product> getAllProducts() {
        // TODO Auto-generated method stub
        return productRepository.findAll();
    }

    @Override
    public Boolean deleteProduct(Integer id) {
        // TODO Auto-generated method stub
        Product product = productRepository.findById(id).orElse(null);
        if (!ObjectUtils.isEmpty(product)) {
            productRepository.delete(product);
            return true;
        }
        return false;
    }

    @Override
    public Product getProductById(Integer id) {
        // TODO Auto-generated method stub
        Product product = productRepository.findById(id).orElse(null);
        return product;
    }

    @Override
    public Product updateProduct(Product product, MultipartFile image) {
        // TODO Auto-generated method stub
        Product oldProduct = getProductById(product.getId());
        String imageName = image.isEmpty() ? oldProduct.getImage() : image.getOriginalFilename();

        oldProduct.setTitle(product.getTitle());
        oldProduct.setDescription(product.getDescription());
        oldProduct.setCategory(product.getCategory());
        oldProduct.setPrice(product.getPrice());
        oldProduct.setStock(product.getStock());
        oldProduct.setImage(imageName);
        oldProduct.setIsActive(product.getIsActive());

        oldProduct.setDiscount(product.getDiscount());

        double dicount = product.getPrice() * (product.getDiscount() / 100.0);
        double discountPrice = product.getPrice() - dicount;
        oldProduct.setDiscountPrice(discountPrice);

        Product updateProduct = productRepository.save(oldProduct);
        if (!ObjectUtils.isEmpty(updateProduct)) {
            if (!image.isEmpty()) {
                try {
                    File saveFile = new ClassPathResource("static/images").getFile();
                    Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + "product_img" + File.separator
                            + image.getOriginalFilename());

                    System.out.println(path);
                    Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
            }
            return product;
        }
        return null;
    }

    @Override
    public List<Product> getAllActiveProducts(String category) {
        List<Product> products = null;
        if (ObjectUtils.isEmpty(category)) {
            products = productRepository.findByIsActiveTrue();
        } else {
            products = productRepository.findByCategory(category);
        }
        return products;
    }

}
