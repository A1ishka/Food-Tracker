package com.makogon.foodtracker.controller;

import com.makogon.foodtracker.model.*;
import com.makogon.foodtracker.service.CategoryService;
import com.makogon.foodtracker.service.PersonService;
import com.makogon.foodtracker.service.ProductService;
import com.makogon.foodtracker.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final StatisticsService statisticsService;
    private final PersonService personService;
    @Autowired
    public ProductController(ProductService productService, CategoryService categoryService, StatisticsService statisticsService, PersonService personService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.statisticsService = statisticsService;
        this.personService = personService;
    }
    @GetMapping("/categories")
    public String showCategories(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        return "categories";
    }
    @GetMapping("/category/{id}")
    public String showProductsByCategory(@PathVariable("id") Long categoryId, Model model) {
        Category category = categoryService.getCategoryById(categoryId);
        model.addAttribute("category", category);
        model.addAttribute("products", productService.getProductsByCategory(category));
        return "category_products";
    }
    @GetMapping("/product/{id}")
    public String showProductDetails(@PathVariable("id") Long productId, Model model) {
        Product product = productService.getProductById(productId);
        model.addAttribute("product", product);
        return "product_details";
    }
    @PostMapping("/addProductToStatistics")
    public ResponseEntity<String> addProductToStatistics(@RequestParam long productId, @RequestParam long statisticsId, @RequestParam long personId, @RequestParam float weight) {
        try {
            Product product = productService.getProductById(productId);
            Statistics statistics = statisticsService.getStatisticsById(statisticsId);

            ProductWeight productWeight = new ProductWeight();
            productWeight.setProduct(product);
            productWeight.setStatistics(statistics);
            productWeight.setWeight(weight);

            //statistics.getProductWeights().add(productWeight);
            //statisticsService.saveStatistics(statistics);

            Person person = personService.getPersonById(personId);
            personService.savePerson(person);

            return ResponseEntity.ok("Товар успешно добавлен к статистике.");
        } catch (Exception e) {
            return ResponseEntity.ok(String.valueOf(e));
        }
    }
}