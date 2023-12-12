package com.makogon.foodtracker.controller;

import com.makogon.foodtracker.model.Category;
import com.makogon.foodtracker.model.Product;
import com.makogon.foodtracker.service.CategoryService;
import com.makogon.foodtracker.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class CategoryController {

    private final ProductService productService;
    private final CategoryService categoryService;

    public CategoryController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }
    @PostMapping("/viewcategories")
    public String viewCategoriesPage() {
        return "redirect:/categories";
    }
    @GetMapping("/category/{categoryID}")
    public String showProductsByCategory(@PathVariable("categoryID") Long categoryID, Model model) {
        Category category = categoryService.getCategoryById(categoryID);
        List<Product> products= productService.getProductsByCategory(category);
        model.addAttribute("category", category);
        model.addAttribute("products", products);
        return "category_products";
    }
    @GetMapping("/categories")
    public String showCategories(Model model) {
        List<Category> categories= categoryService.getAllCategories();
        List<Product> products= productService.getAllProducts();
        model.addAttribute("categories", categories);
        model.addAttribute("products", products);
        return "categories";
    }
}
