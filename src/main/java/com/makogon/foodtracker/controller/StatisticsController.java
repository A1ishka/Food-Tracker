package com.makogon.foodtracker.controller;

import com.makogon.foodtracker.model.BasePlan;
import com.makogon.foodtracker.model.Person;
import com.makogon.foodtracker.model.Statistics;
import com.makogon.foodtracker.model.User;
import com.makogon.foodtracker.repository.StatisticsRepository;
import com.makogon.foodtracker.service.PersonService;
import com.makogon.foodtracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Controller
public class StatisticsController {
    private UserService userService;
    private PersonService personService;
    private StatisticsRepository statisticsRepository;

    @Autowired
    public StatisticsController(UserService userService, PersonService personService, StatisticsRepository statisticsRepository) {
        this.userService = userService;
        this.personService = personService;
        this.statisticsRepository = statisticsRepository;
    }

//    @GetMapping("/statistics/{login}/{stringDate}")
//    public String showStatistics(Model model, @RequestParam("login") String login, @RequestParam("stringDate") String stringDate) {
//        User user = userService.getUserByLogin(login);
//        Person person = personService.getPersonByUser(user);
//        BasePlan basePlan = person.getBasePlan();
//        LocalDate date = LocalDate.parse(stringDate, DateTimeFormatter.ISO_DATE);
//        Statistics statistics = statisticsRepository.findByPersonAndDate(person, date);
////не точно, что это вообще работает
//        model.addAttribute(basePlan);
//        model.addAttribute(statistics);
//        return "dailystatictics";
//    }

    @PostMapping("/statistics")
    public String updateStatistics(@RequestParam("calories") float consumedCalories,
                                   @RequestParam("protein") float consumedProtein,
                                   @RequestParam("fats") float consumedFat,
                                   @RequestParam("carbs") float consumedCarbohydrates) {

        return "redirect:/statistics";
    }

}