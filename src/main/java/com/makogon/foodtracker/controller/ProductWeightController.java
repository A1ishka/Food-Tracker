package com.makogon.foodtracker.controller;

import com.makogon.foodtracker.model.Person;
import com.makogon.foodtracker.model.Product;
import com.makogon.foodtracker.model.ProductWeight;
import com.makogon.foodtracker.model.Statistics;
import com.makogon.foodtracker.service.PersonService;
import com.makogon.foodtracker.service.ProductService;
import com.makogon.foodtracker.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@Controller
public class ProductWeightController {
    private final ProductService productService;
    private final StatisticsService statisticsService;
    private final PersonService personService;

    @Autowired
    public ProductWeightController(ProductService productService, StatisticsService statisticsService, PersonService personService) {
        this.productService = productService;
        this.statisticsService = statisticsService;
        this.personService = personService;
    }

    @PostMapping("/addProductToStatistics")
    public ResponseEntity<String> addProductToStatistics(@RequestParam long productId, @RequestParam long statisticsId, @RequestParam long personId, @RequestParam float weight) {
        try {
            Product product = productService.getProductById(productId);
            Statistics statistics = statisticsService.getStatisticsById(statisticsId);
            Person person = personService.getPersonById(personId);
            ProductWeight productWeight = new ProductWeight();
            Date date = new Date();

            statistics.setPerson(person);
            productWeight.setProduct(product);
            productWeight.setWeight(weight);
            productWeight.setAdd_time(date.toString());

            float portion = productWeight.getWeight()/100;

            statistics.setCalories(product.getCalories()*portion);
            statistics.setCalories(product.getFats()*portion);
            statistics.setCalories(product.getCarbs()*portion);
            statistics.setCalories(product.getProtein()*portion);

            productWeight.setStatistics(statistics);
            //statistics.getProductWeights().add(productWeight);
            //statisticsService.saveStatistics(statistics);

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
}
