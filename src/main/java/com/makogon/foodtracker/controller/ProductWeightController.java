package com.makogon.foodtracker.controller;

import com.makogon.foodtracker.model.Product;
import com.makogon.foodtracker.model.ProductWeight;
import com.makogon.foodtracker.model.Statistics;
import com.makogon.foodtracker.model.User;
import com.makogon.foodtracker.service.PersonService;
import com.makogon.foodtracker.service.ProductService;
import com.makogon.foodtracker.service.StatisticsService;
import com.makogon.foodtracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
public class ProductWeightController {
    private final ProductService productService;
    private final StatisticsService statisticsService;
    private final PersonService personService;

    private final UserService userService;

    @Autowired
    public ProductWeightController(ProductService productService, StatisticsService statisticsService, PersonService personService, UserService userService) {
        this.productService = productService;
        this.statisticsService = statisticsService;
        this.personService = personService;
        this.userService = userService;
    }

    @PostMapping("/addproduct")
    public ResponseEntity<String> addProductToStatistics(@RequestParam String productName, @RequestParam long statisticsID, @RequestParam String userID, @RequestParam float weight) {
        try {
            Product product = productService.getProductByName(productName);
            Statistics statistics = statisticsService.getStatisticsById(statisticsID);
            long long_userID = Long.valueOf(userID);
            User user = userService.getUserByID(long_userID);
            ProductWeight productWeight = new ProductWeight();
            Date date = new Date();

            statistics.setPerson(user.getPerson());
            productWeight.setProduct(product);
            productWeight.setWeight(weight);
            productWeight.setAdd_time(date.toString());

            float portion = productWeight.getWeight() / 100;

            if (statistics.getCalories() == 0) {
                statistics.setCalories(product.getCalories() * portion);
                statistics.setFats(product.getFats() * portion);
                statistics.setCarbs(product.getCarbs() * portion);
                statistics.setProtein(product.getProtein() * portion);
            } else {
                statistics.setCalories(statistics.getCalories() + product.getCalories() * portion);
                statistics.setFats(statistics.getFats() + product.getFats() * portion);
                statistics.setCarbs(statistics.getCarbs() + product.getCarbs() * portion);
                statistics.setProtein(statistics.getProtein() + product.getProtein() * portion);
            }

            productWeight.setStatistics(statistics);
            //statistics.getProductWeights().add(productWeight);
            statisticsService.saveStatistics(statistics);

            //personService.savePerson(person);

            return ResponseEntity.ok("Продукт успешно добавлен к статистике.");
        } catch (Exception e) {
            return ResponseEntity.ok(String.valueOf(e));
        }
    }

    @DeleteMapping("/removeProductFromStatistics")
    public ResponseEntity<String> removeProductFromStatistics(@RequestParam long productId, @RequestParam long statisticsId, @RequestParam long personId) {
        try {
            Product product = productService.getProductById(productId);
            Statistics statistics = statisticsService.getStatisticsById(statisticsId);


            return ResponseEntity.ok("Продукт успешно удален из статистики.");
        } catch (Exception e) {
            return ResponseEntity.ok(String.valueOf(e));
        }
    }

    @PutMapping("/editProductWeight")
    public ResponseEntity<String> editProductWeight(@RequestParam long productId, @RequestParam long statisticsId, @RequestParam long personId, @RequestParam float newWeight) {
        try {


            return ResponseEntity.ok("Продукт успешно обновлен в статистике.");
        } catch (Exception e) {
            return ResponseEntity.ok(String.valueOf(e));
        }
    }

    @GetMapping("/addproduct")
    public String addProduct(Model model, @RequestParam("statisticsID") Long statisticsID) {
        List<Product> products = productService.findAll();
        model.addAttribute("products", products);
        model.addAttribute("statisticsID", statisticsID);
        return "addproduct";
    }
}
