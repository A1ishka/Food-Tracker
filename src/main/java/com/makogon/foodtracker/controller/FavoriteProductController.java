package com.makogon.foodtracker.controller;

import com.makogon.foodtracker.model.*;
import com.makogon.foodtracker.repository.CategoryRepository;
import com.makogon.foodtracker.repository.FavoriteProductRepository;
import com.makogon.foodtracker.repository.ProductWeightRepository;
import com.makogon.foodtracker.repository.StatisticsRepository;
import com.makogon.foodtracker.service.PersonService;
import com.makogon.foodtracker.service.ProductService;
import com.makogon.foodtracker.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Controller
public class FavoriteProductController {
    private final ProductService productService;
    private final CategoryRepository categoryRepository;
    private final FavoriteProductRepository favoriteProductRepository;
    private final UserService userService;
    private final PersonService personService;
    private final StatisticsRepository statisticsRepository;
    private final ProductWeightRepository productWeightRepository;

    @Autowired
    public FavoriteProductController(ProductService productService, CategoryRepository categoryRepository, FavoriteProductRepository favoriteProductRepository, UserService userService, PersonService personService, StatisticsRepository statisticsRepository, ProductWeightRepository productWeightRepository) {
        this.productService = productService;
        this.categoryRepository = categoryRepository;
        this.favoriteProductRepository = favoriteProductRepository;
        this.userService = userService;
        this.personService = personService;
        this.statisticsRepository = statisticsRepository;
        this.productWeightRepository = productWeightRepository;
    }

    @PostMapping("/addtofavorite")
    public void addtofavorite(@RequestParam("productID") Long productID, @RequestParam("userID") Long userID) {
        User user = userService.getUserByID(userID);
        Person person = user.getPerson();
        Product product = productService.getProductById(productID);

        FavoriteProduct favoriteProduct = new FavoriteProduct();
        favoriteProduct.setProduct(product);
        favoriteProduct.setPerson(person);

        favoriteProductRepository.save(favoriteProduct);
    }

    @PostMapping("/addProductToFavorites")
    public void addProductToFavorites(@RequestParam("productName") String productName, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        long userID = 0;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("userID".equals(cookie.getName())) {
                    if (cookie.getValue() != null) {
                        //userID = Long.getLong(cookie.getValue());
                        userID = 12;
                        break;
                    }
                }
            }
        }
        User user = userService.getUserByID(userID);
        Person person = user.getPerson();
        Product product = productService.getProductByName(productName);

        FavoriteProduct favoriteProduct = new FavoriteProduct();
        favoriteProduct.setProduct(product);
        favoriteProduct.setPerson(person);

        favoriteProductRepository.save(favoriteProduct);
    }

//    @PostMapping("/viewfavorite/{userID}")
//    public String viewFavorite(@PathVariable("userID") Long userID, RedirectAttributes redirectAttributes) {
//        redirectAttributes.addAttribute("userID", userID);
//        return "redirect:/myfavorite";
//    }
    @PostMapping("/viewfavorite")
    public String viewFavorite() {
        return "redirect:/myfavorite";
    }
    @GetMapping("/myfavorite")
    public String favoriteProducts(HttpServletRequest request, Model model) {
        Cookie[] cookies = request.getCookies();
        long userID = 0;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("userID".equals(cookie.getName())) {
                    if (cookie.getValue() != null) {
                        //userID = Long.getLong(cookie.getValue());
                        userID = 12;
                        break;
                    }
                }
            }
        }
        User user = userService.getUserByID(userID);
        List<FavoriteProduct> favoriteProducts = favoriteProductRepository.findAllByPerson(user.getPerson());
        model.addAttribute("favoriteProducts", favoriteProducts);
        model.addAttribute("userID", userID);
        return "favoriteproducts";
    }
    @PostMapping("/myfavorite")
    public ResponseEntity<String> addFavoritesToStatistics(@RequestParam("favoriteProductID") long favoriteProductID, @RequestParam("weight") float weight) {
        FavoriteProduct favoriteProduct = favoriteProductRepository.getFavoriteProductByfavoriteproductID(favoriteProductID);
        Person person = favoriteProduct.getPerson();
        Product product = favoriteProduct.getProduct();
        Statistics statistics = new Statistics();

        try {
            ProductWeight productWeight = new ProductWeight();
            productWeight.setProduct(product);
            productWeight.setWeight(Float.valueOf(weight));

            try {
                statistics = statisticsRepository.findByPersonAndDate(person, LocalDate.now());
            } catch (Exception e) {
                System.out.println(e);
            }finally {
                productWeight.setAdd_time( LocalDate.now().toString());
                statistics.setPerson(person);
            }

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
            productWeightRepository.save(productWeight);
            statisticsRepository.save(statistics);

            return ResponseEntity.ok("Продукт успешно добавлен к статистике.");
        } catch (Exception e) {
            return ResponseEntity.ok(String.valueOf(e));
        }
    }
    @PostMapping("/removeFromFavorites")
    public ResponseEntity<String> removeFromFavorites(@RequestParam("favoriteProductID") long favoriteProductID) {
        favoriteProductRepository.deleteById(favoriteProductID);
        return ResponseEntity.ok("Продукт успешно удален из избранных.");
    }
}
