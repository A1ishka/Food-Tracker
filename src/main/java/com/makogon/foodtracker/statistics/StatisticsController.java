package com.makogon.foodtracker.statistics;

import com.makogon.foodtracker.model.BasePlan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class StatisticsController {
    @GetMapping("/statistics")
    public String showStatistics(Model model) {

        return "statistics";
    }

    @PostMapping("/statistics")
    public String updateStatistics(@RequestParam("calories") int consumedCalories,
                                   @RequestParam("protein") int consumedProtein,
                                   @RequestParam("fats") int consumedFat,
                                   @RequestParam("carbs") int consumedCarbohydrates) {

        return "redirect:/statistics";
    }

}