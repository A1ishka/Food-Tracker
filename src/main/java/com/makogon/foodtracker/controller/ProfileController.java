package com.makogon.foodtracker.controller;

import com.makogon.foodtracker.model.*;
import com.makogon.foodtracker.repository.*;
import com.makogon.foodtracker.service.MyUserDetailsService;
import com.makogon.foodtracker.service.PersonService;
import com.makogon.foodtracker.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/editprofile/{userID}")
    public String showProfilePage(@PathVariable("userID") Long userID, Model model) {
        User user = userService.getUserByID(userID);
        Person person = personService.getPersonByUser(user);
        UserDetails userDetails = userDetailsService.getUserDetailsByPerson(person);
        model.addAttribute("user", user);
        model.addAttribute("person", person);
        model.addAttribute("userDetails", userDetails);
        return "editProfile";
    }
    @PostMapping("/vieweditprofile/{userID}")
    public String viewProfilePage(@PathVariable("userID") Long userID, RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("userID", userID);
        return "redirect:/editProfile/{userID}";
    }
    @PostMapping("/editprofile/{userID}/password")
    public String editProfilePassword(@PathVariable("userID") Long userID,
                                      @RequestParam("password") String password) {
        User user = userService.getUserByID(userID);
        user.setLogin(user.getLogin());
        user.setPassword(password);
        user.setPerson(user.getPerson());
        user.setRole(user.getRole());

        userRepository.save(user);
        return "redirect:/categories";
    }

    @PostMapping("/editprofile/{userID}/personaldata")
    public String editProfilePersonalData(@PathVariable("userID") Long userID,
                                          @RequestParam("firstName") String firstName,
                                          @RequestParam("lastName") String lastName,
                                          @RequestParam("age") Integer age,
                                          @RequestParam("sex") String sex) {
        User user = userService.getUserByID(userID);
        Person person = user.getPerson();
        UserDetails userDetails = userDetailsService.getUserDetailsByPerson(person);
        Activity activity = userDetails.getActivity();

        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setBasePlan(person.getBasePlan());

        userDetails.setHeight(userDetails.getHeight());
        userDetails.setWeight(userDetails.getWeight());
        userDetails.setAge(age);
        userDetails.setActivity(activity);
        userDetails.setSex(sex);
        userDetails.setPerson(person);

        userDetailsRepository.save(userDetails);
        personRepository.save(person);
        return "redirect:/categories";
    }
    @PostMapping("/editprofile/{userID}/bodydata")
    public String editProfileBodyData(@PathVariable("userID") Long userID,
                                      @RequestParam("weight") Float weight,
                                      @RequestParam("height") Float height,
                                      @RequestParam("activityLevel") String activityLevel) {
        User user = userService.getUserByID(userID);
        UserDetails userDetails = user.getPerson().getUserDetails();
        Activity activity = activityRepository.findByactivityName(activityLevel).orElse(null);

        userDetails.setHeight(height);
        userDetails.setWeight(weight);
        userDetails.setAge(userDetails.getAge());
        userDetails.setActivity(activity);
        userDetails.setSex(userDetails.getSex());
        userDetails.setPerson(userDetails.getPerson());

        userDetailsRepository.save(userDetails);
        return "redirect:/categories";
    }
    @PostMapping("/editprofile/{userID}/plan")
    public String editProfilePlan(@PathVariable("userID") Long userID,
                                  @RequestParam("calories") Float calories,
                                  @RequestParam("protein") Float protein,
                                  @RequestParam("fats") Float fats,
                                  @RequestParam("carbs") Float carbs,
                                  @RequestParam("plan") String plan) {
        User user = userService.getUserByID(userID);
        Person person = personService.getPersonByUser(user);
        BasePlan basePlan = person.getBasePlan();
        Plan planName = planRepository.findByplanName(plan).orElse(null);

        basePlan.setFats(fats);
        basePlan.setCarbs(carbs);
        basePlan.setProtein(protein);
        basePlan.setCalories(calories);
        basePlan.setPlan(planName);

        person.setFirstName(person.getFirstName());
        person.setLastName(person.getLastName());
        person.setBasePlan(basePlan);

        basePlanRepository.save(basePlan);
        personRepository.save(person);
        return "redirect:/categories";
    }

    @GetMapping("/questions")
    public String showQuestions() {
        return "questions";
    }

//    @PostMapping("/editprofile/{userID}")
//    public String editProfilePage(@PathVariable("userID") Long userID,
//                                  @RequestParam("password") String password,
//                                  @RequestParam("firstName") String firstName,
//                                  @RequestParam("lastName") String lastName,
//                                  @RequestParam("age") Integer age,
//                                  @RequestParam("sex") String sex,
//                                  @RequestParam("weight") Float weight,
//                                  @RequestParam("height") Float height,
//                                  @RequestParam("activityLevel") String activityLevel,
//                                  @RequestParam("calories") Float calories,
//                                  @RequestParam("protein") Float protein,
//                                  @RequestParam("fats") Float fats,
//                                  @RequestParam("carbs") Float carbs,
//                                  @RequestParam("plan") String plan) {
//        User user = userService.getUserByID(userID);
//        Person person = personService.getPersonByUser(user);
//        UserDetails userDetails = userDetailsService.getUserDetailsByPerson(person);
//        BasePlan basePlan = person.getBasePlan();
//        Plan planName = planRepository.findByplanName(plan).orElse(null);
//        Activity activity = activityRepository.findByactivityName(activityLevel).orElse(null);
//
//        basePlan.setFats(fats);
//        basePlan.setCarbs(carbs);
//        basePlan.setProtein(protein);
//        basePlan.setCalories(calories);
//        basePlan.setPlan(planName);
//
//        person.setFirstName(firstName);
//        person.setLastName(lastName);
//        person.setBasePlan(basePlan);
//
//        userDetails.setHeight(height);
//        userDetails.setWeight(weight);
//        userDetails.setAge(age);
//        userDetails.setActivity(activity);
//        userDetails.setSex(sex);
//        userDetails.setPerson(person);
//
//        user.setLogin(user.getLogin());
//        user.setPassword(password);
//        user.setPerson(person);
//        user.setRole(ROLE_USER);
//
//        basePlanRepository.save(basePlan);
//        userDetailsRepository.save(userDetails);
//        personRepository.save(person);
//        userRepository.save(user);
//        return "redirect:/categories";
//    }
}
