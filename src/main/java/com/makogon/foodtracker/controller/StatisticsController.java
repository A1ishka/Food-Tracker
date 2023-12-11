package com.makogon.foodtracker.controller;

import com.makogon.foodtracker.model.*;
import com.makogon.foodtracker.repository.ProductRepository;
import com.makogon.foodtracker.repository.ProductWeightRepository;
import com.makogon.foodtracker.repository.StatisticsRepository;
import com.makogon.foodtracker.service.PersonService;
import com.makogon.foodtracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class StatisticsController {
    private UserService userService;
    private PersonService personService;
    private StatisticsRepository statisticsRepository;
    private ProductWeightRepository productWeightRepository;
    private ProductRepository productRepository;

    @Autowired
    public StatisticsController(UserService userService, PersonService personService, StatisticsRepository statisticsRepository, ProductWeightRepository productWeightRepository, ProductRepository productRepository) {
        this.userService = userService;
        this.personService = personService;
        this.statisticsRepository = statisticsRepository;
        this.productWeightRepository = productWeightRepository;
        this.productRepository = productRepository;
    }

    @GetMapping("/statistics/{statisticsID}")
    public String showStatistics(@PathVariable("statisticsID") Long statisticsID, Model model) {
        Statistics statistics = statisticsRepository.findById(statisticsID)
                .orElseThrow(() -> new IllegalArgumentException("Invalid statistics ID"));
        Person person = statistics.getPerson();
        BasePlan basePlan = person.getBasePlan();
        List<ProductWeight> productWeights = productWeightRepository.findByStatistics(statistics);

        model.addAttribute("basePlan", basePlan);
        model.addAttribute("statistics", statistics);
        model.addAttribute("productWeights", productWeights);
        return "dailystatictics";
    }
    @PostMapping("/statistics/{statisticsID}")
    public String sendStatisticsID(@PathVariable("statisticsID") Long statisticsID, RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("statisticsID", statisticsID);
        return "redirect:/addproduct";
    }

    @PostMapping("/viewstatistics/{statisticsID}")
    public String viewStatisticsID(@PathVariable("statisticsID") Long statisticsID, RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("statisticsID", statisticsID);
        return "redirect:/statistics/{statisticsID}";
    }
    @GetMapping("/statistics")
    public String getStatisticsPage(@RequestParam(defaultValue = "0") int page, Model model) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<Statistics> statisticsPage = statisticsRepository.findAll(pageable);
        model.addAttribute("statisticsPage", statisticsPage);
        model.addAttribute("currentPage", page);
        return "statistics";
    }
    @PostMapping("/viewstatistics")
    public String viewStatisticsPage() { return "redirect:/statistics"; }
    @PostMapping("/statistics/{statisticsId}/addProduct")
    public String addProductToStatistics(@PathVariable Long statisticsId,
                                         @RequestParam Long productId,
                                         @RequestParam float weight) {
        Statistics statistics = statisticsRepository.findById(statisticsId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid statistics ID"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product ID"));

        float calories = statistics.getCalories() + (product.getCalories() * weight);
        float protein = statistics.getProtein() + (product.getProtein() * weight);
        float fats = statistics.getFats() + (product.getFats() * weight);
        float carbs = statistics.getCarbs() + (product.getCarbs() * weight);

        statistics.setCalories(calories);
        statistics.setProtein(protein);
        statistics.setFats(fats);
        statistics.setCarbs(carbs);

        Statistics updatedStatistics = statisticsRepository.save(statistics);

        ProductWeight productWeight = new ProductWeight();
        productWeight.setAdd_time(String.valueOf(LocalDateTime.now()));
        productWeight.setProduct(product);
        productWeight.setWeight(weight);
        productWeight.setStatistics(updatedStatistics);

        productWeightRepository.save(productWeight);

        return "redirect:/statistics/" + statisticsId;
    }
}