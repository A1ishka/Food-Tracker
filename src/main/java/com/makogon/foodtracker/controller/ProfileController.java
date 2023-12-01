package com.makogon.foodtracker.controller;

import com.makogon.foodtracker.model.*;
import com.makogon.foodtracker.repository.*;
import com.makogon.foodtracker.service.MyUserDetailsService;
import com.makogon.foodtracker.service.PersonService;
import com.makogon.foodtracker.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ProfileController {
    private final UserService userService;
    private final PersonService personService;
    private final MyUserDetailsService userDetailsService;
    private final UserRepository userRepository;
    private final MyUserDetailsRepository userDetailsRepository;
    private final PersonRepository personRepository;
    private final PlanRepository planRepository;
    private final ActivityRepository activityRepository;
    private final BasePlanRepository basePlanRepository;

    public ProfileController(UserService userService, PersonService personService, MyUserDetailsService userDetailsService, UserRepository userRepository, MyUserDetailsRepository userDetailsRepository, PersonRepository personRepository, PlanRepository planRepository, ActivityRepository activityRepository, BasePlanRepository basePlanRepository) {
        this.userService = userService;
        this.personService = personService;
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
        this.userDetailsRepository = userDetailsRepository;
        this.personRepository = personRepository;
        this.planRepository = planRepository;
        this.activityRepository = activityRepository;
        this.basePlanRepository = basePlanRepository;
    }

    @GetMapping("/profile")
    public String showRegisterPage() {
        return "registration";
    }

    @PostMapping("/profile")
    public String registerUser(@RequestParam("password") String password,
                               @RequestParam("firstName") String firstName,
                               @RequestParam("lastName") String lastName,
                               @RequestParam("age") Integer age,
                               @RequestParam("sex") String sex,
                               @RequestParam("weight") Float weight,
                               @RequestParam("height") Float height,
                               @RequestParam("activityLevel") String activityLevel,
                               @RequestParam("calories") Float calories,
                               @RequestParam("protein") Float protein,
                               @RequestParam("fats") Float fats,
                               @RequestParam("carbs") Float carbs,
                               @RequestParam("plan") String plan,
                               RedirectAttributes redirectAttributes) {

        Plan planName = planRepository.findByplanName(plan).orElse(null);
        BasePlan basePlan = new BasePlan();
        Activity activity = activityRepository.findByactivityName(activityLevel).orElse(null);
        Person person = new Person();
        UserDetails userDetails = new UserDetails();
        User user = new User();

        basePlan.setFats(fats);
        basePlan.setCarbs(carbs);
        basePlan.setProtein(protein);
        basePlan.setCalories(calories);
        basePlan.setPlan(planName);

        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setBasePlan(basePlan);

        userDetails.setHeight(height);
        userDetails.setWeight(weight);
        userDetails.setAge(age);
        userDetails.setActivity(activity);
        userDetails.setSex(sex);
        userDetails.setPerson(person);

        user.setPassword(password);
        user.setPerson(person);

        basePlanRepository.save(basePlan);
        userDetailsRepository.save(userDetails);
        personRepository.save(person);
        userRepository.save(user);

        return "redirect:/completed";
    }
}
