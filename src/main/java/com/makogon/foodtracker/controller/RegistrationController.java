package com.makogon.foodtracker.controller;

import com.makogon.foodtracker.auth.AuthenticationRequest;
import com.makogon.foodtracker.auth.AuthenticationResponse;
import com.makogon.foodtracker.auth.AuthenticationService;
import com.makogon.foodtracker.model.*;
import com.makogon.foodtracker.register.RegisterRequest;
import com.makogon.foodtracker.repository.*;
import com.makogon.foodtracker.service.MyUserDetailsService;
import com.makogon.foodtracker.service.PersonService;
import com.makogon.foodtracker.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

import static com.makogon.foodtracker.model.Role.ROLE_USER;

@Controller
public class RegistrationController {
    private final UserService userService;
    private final PersonService personService;
    private final MyUserDetailsService userDetailsService;
    private final UserRepository userRepository;
    private final MyUserDetailsRepository userDetailsRepository;
    private final PersonRepository personRepository;
    private final PlanRepository planRepository;
    private final ActivityRepository activityRepository;
    private final BasePlanRepository basePlanRepository;
    private final AuthenticationService authenticationService;

    public RegistrationController(UserService userService, PersonService personService, MyUserDetailsService userDetailsService, UserRepository userRepository, MyUserDetailsRepository userDetailsRepository, PersonRepository personRepository, PlanRepository planRepository, ActivityRepository activityRepository, BasePlanRepository basePlanRepository, AuthenticationService authenticationService) {
        this.userService = userService;
        this.personService = personService;
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
        this.userDetailsRepository = userDetailsRepository;
        this.personRepository = personRepository;
        this.planRepository = planRepository;
        this.activityRepository = activityRepository;
        this.basePlanRepository = basePlanRepository;
        this.authenticationService = authenticationService;
    }

    @GetMapping("/register")
    public String showRegisterPage() {
        return "registration";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam("login") String login,
                               @RequestParam("password") String password,
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
                               RedirectAttributes redirectAttributes,
                               HttpServletRequest request) {
//        if (!userService.isLoginUnique(login)) {
//            redirectAttributes.addFlashAttribute("errorMessage", "Логин уже занят");
//            return "redirect:/registration";
//        }

        Plan planName = planRepository.findByplanName(plan).orElse(null);
        BasePlan basePlan = new BasePlan();
        Activity activity = activityRepository.findByactivityName(activityLevel).orElse(null);
        User user = new User();
        Person person = user.getPerson();
        UserDetails userDetails = new UserDetails();

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

//        user.setLogin(login);
//        user.setPassword(password);
//        user.setPerson(person);
//        user.setRole(ROLE_USER);

        basePlanRepository.save(basePlan);
        userDetailsRepository.save(userDetails);
        personRepository.save(person);
        //userRepository.save(user);

        RegisterRequest registerRequest = new RegisterRequest(login, password, person, ROLE_USER);
        authenticationService.register(registerRequest);

        return "redirect:/completed";
    }

    @GetMapping("/completed")
    public String showRegRes() {
        return "completedRegistration";
    }

    @GetMapping("/user")
    public String showUser() {
        return "user";
    }

    @GetMapping("/admin")
    public String showAdmin() {
        return "admin";
    }

    @PostMapping("/checkLogin")
    @ResponseBody
    public Map<String, Boolean> checkLoginAvailability(@RequestParam("login") String login) {
        boolean isUnique = userService.isLoginUnique(login);
        Map<String, Boolean> response = new HashMap<>();
        response.put("unique", isUnique);
        return response;
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam("login") String login,
                            @RequestParam("password") String password,
                            HttpServletResponse response) {
        User user = userService.getUserByLogin(login);
        AuthenticationRequest authRequest = new AuthenticationRequest(login, password/*, user.getPerson(), user.getRole()*/);
        authenticationService.authenticate(authRequest);
        AuthenticationResponse authenticationResponse = authenticationService.authenticate(authRequest);
        Cookie cookie = new Cookie("token", authenticationResponse.getAccessToken());
        //cookie.setMaxAge(86400); // Установка срока действия куки (например, 24 часа)
        cookie.setPath("/"); // Установка пути, для которого будет доступна кука (например, весь сайт)
        response.addCookie(cookie);
        Cookie roleCookie = new Cookie("userRole", user.getRole().toString());
        roleCookie.setPath("/");
        response.addCookie(roleCookie);
        return "redirect:/categories";
    }


}