package com.makogon.foodtracker.controller;

import com.makogon.foodtracker.model.Category;
import com.makogon.foodtracker.model.Product;
import com.makogon.foodtracker.repository.CategoryRepository;
import com.makogon.foodtracker.service.CategoryService;
import com.makogon.foodtracker.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ProductController {
    private final ProductService productService;
    private final CategoryRepository categoryRepository;


    @Autowired
    public ProductController(ProductService productService, CategoryRepository categoryRepository) {
        this.productService = productService;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/product/{productID}")
    public String showProductDetails(@PathVariable("productID") Long productID, Model model) {
        Product product = productService.getProductById(productID);
        model.addAttribute("product", product);
        return "product_details";
    }
    @GetMapping("/editproduct/{productID}")
    public String showEditingProductDetails(@PathVariable("productID") Long productID, Model model) {
        Product product = productService.getProductById(productID);
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("product", product);
        model.addAttribute("categories", categories);
        return "editProduct";
    }
}