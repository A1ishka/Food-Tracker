package com.makogon.foodtracker.controller;

import com.makogon.foodtracker.model.Category;
import com.makogon.foodtracker.model.Product;
import com.makogon.foodtracker.model.User;
import com.makogon.foodtracker.repository.CategoryRepository;
import com.makogon.foodtracker.service.CategoryService;
import com.makogon.foodtracker.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    @PutMapping("/editproduct/{productID}")
    public String editProduct(@PathVariable Long productID, @RequestBody Product updatedProduct) {
        productService.updateProduct(productID, updatedProduct);
        return "redirect:/product_details";
    }
    @DeleteMapping("/deleteproduct/{productID}")
    public String deleteProduct(@PathVariable Long productID) {
        productService.deleteProductById(productID);
        return "redirect:/categories";
    }
}
