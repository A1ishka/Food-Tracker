package com.makogon.foodtracker.controller;

import com.makogon.foodtracker.model.*;
import com.makogon.foodtracker.service.UserService;
import com.makogon.foodtracker.repository.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

@Controller
public class RegistrationController {
    private final UserService userService;
    private final UserRepository userRepository;
    private final UserDetailsRepository userDetailsRepository;
    private final PersonRepository personRepository;
    private final PlanRepository planRepository;
    private final ActivityRepository activityRepository;
    private final BasePlanRepository basePlanRepository;

    public RegistrationController(UserService userService, UserRepository userRepository, UserDetailsRepository userDetailsRepository, PersonRepository personRepository, PlanRepository planRepository, ActivityRepository activityRepository, BasePlanRepository basePlanRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.userDetailsRepository = userDetailsRepository;
        this.personRepository = personRepository;
        this.planRepository = planRepository;
        this.activityRepository = activityRepository;
        this.basePlanRepository = basePlanRepository;
    }

    @GetMapping("/register")
    public String showLoginPage() {
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
                               RedirectAttributes redirectAttributes) {
        if (!userService.isLoginUnique(login)) {
            redirectAttributes.addFlashAttribute("errorMessage", "Логин уже занят");
            return "redirect:/registration";
        }

        Plan planName = planRepository.findByplanName(plan).orElse(null);
        BasePlan basePlan = new BasePlan();
        Activity activity = activityRepository.findByactivityName(activityLevel).orElse(null);
        Person person = new Person();
        UserDetails userDetails = new UserDetails(11);
        User user = new User();

        basePlan.setFats(fats);
        basePlan.setCarbs(carbs);
        basePlan.setProtein(protein);
        basePlan.setCalories(calories);
        basePlan.setPlan(planName);

        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setPlanName(plan);
        person.setBasePlan(basePlan);
        person.setUserDetails(userDetails);

        userDetails.setHeight(height);
        userDetails.setWeight(weight);
        userDetails.setAge(age);
        userDetails.setActivity(activity);
        userDetails.setSex(sex);
        userDetails.setPerson(person);

        user.setLogin(login);
        user.setPassword(password);
        user.setPerson(person);

        basePlanRepository.save(basePlan);
        userDetailsRepository.save(userDetails);
        personRepository.save(person);
        userRepository.save(user);

        return "redirect:/completed";
    }

    @GetMapping("/completed")
    public String showRegRes() {
        return "completedRegistration";
    }
//    @PostMapping("/register")
//    public String processLoginPage(@PathVariable(value = "login") String login, RedirectAttributes redirectAttributes) {
//        if (!userService.isLoginUnique(login)) {
//            redirectAttributes.addFlashAttribute("errorMessage", "Логин уже занят");
//        }
//        return "redirect:/register";
//    }
//
//    @GetMapping("/halls/{login}/{password}/{firstName}/{lastName}/{age}/{sex}/{weight}/{height}/{activityLevel}/{calories}/{protein}/{fats}/{carbs}/{plan}")
//    public String completeRegistry(Model model, @PathVariable(value = "login") String login,
//                              @PathVariable(value = "password") String password,
//                              @PathVariable(value = "firstName") String firstName,
//                              @PathVariable(value = "lastName") String lastName,
//                              @PathVariable(value = "age") Integer age,
//                              @PathVariable(value = "sex") String sex,
//                              @PathVariable(value = "weight") Float weight,
//                              @PathVariable(value = "height") Float height,
//                              @PathVariable(value = "activityLevel") String activityLevel,
//                              @PathVariable(value = "calories") Float calories,
//                              @PathVariable(value = "protein") Float protein,
//                              @PathVariable(value = "fats") Float fats,
//                              @PathVariable(value = "carbs") Float carbs,
//                              @PathVariable(value = "plan") String plan) {
//
//        return "completedRegister";
//    }


    @PostMapping("/checkLogin")
    @ResponseBody
    public Map<String, Boolean> checkLoginAvailability(@RequestParam("login") String login) {
        boolean isUnique = userService.isLoginUnique(login);
        Map<String, Boolean> response = new HashMap<>();
        response.put("unique", isUnique);
        return response;
    }


//    @GetMapping("/register/personal")
//    public String showPersonalPage(Model model, @RequestParam("login") String login, @RequestParam("password") String password) {
//        model.addAttribute("login", login);
//        model.addAttribute("password", password);
//        return "personal";
//    }
//
//    @PostMapping("/register/personal")
//    public String processPersonalPage(@RequestParam("login") String login, @RequestParam("password") String password, @RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName, @RequestParam("age") int age, @RequestParam("sex") String sex, RedirectAttributes redirectAttributes) {
//        redirectAttributes.addAttribute("login", login);
//        redirectAttributes.addAttribute("password", password);
//        redirectAttributes.addAttribute("firstName", firstName);
//        redirectAttributes.addAttribute("lastName", lastName);
//        redirectAttributes.addAttribute("age", age);
//        redirectAttributes.addAttribute("sex", sex);
//        return "redirect:/register/calculation";
//    }
//
//    @GetMapping("/register/calculation")
//    public String showCalculationPage(Model model, @RequestParam("login") String login, @RequestParam("password") String password, @RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName, @RequestParam("age") int age, @RequestParam("sex") String sex) {
//        model.addAttribute("login", login);
//        model.addAttribute("password", password);
//        model.addAttribute("firstName", firstName);
//        model.addAttribute("lastName", lastName);
//        model.addAttribute("age", age);
//        model.addAttribute("sex", sex);
//        return "calculation";
//    }
//
//    @PostMapping("/register/calculation")
//    public String processCalculationPage(Model model, @RequestParam(value = "login", required = false) String login, @RequestParam(value = "password", required = false) String password, @RequestParam(value = "firstName", required = false) String firstName, @RequestParam(value = "lastName", required = false) String lastName, @RequestParam(value = "age", required = false) Integer age, @RequestParam(value = "sex", required = false) String sex, @RequestParam(value = "weight", required = false) Float weight, @RequestParam(value = "height", required = false) Float height, @RequestParam(value = "activityLevel", required = false) String activityLevel, @RequestParam(value = "basalMetabolicRate", required = false) Float basalMetabolicRate, @RequestParam(value = "totalCalories", required = false) Float totalCalories, RedirectAttributes redirectAttributes) {
////        redirectAttributes.addAttribute("login", login);
////        redirectAttributes.addAttribute("password", password);
////        redirectAttributes.addAttribute("firstName", firstName);
////        redirectAttributes.addAttribute("lastName", lastName);
////        redirectAttributes.addAttribute("age", age);
////        redirectAttributes.addAttribute("sex", sex);
////        redirectAttributes.addAttribute("weight", weight);
////        redirectAttributes.addAttribute("height", height);
////        redirectAttributes.addAttribute("activityLevel", activityLevel);
////        redirectAttributes.addAttribute("basalMetabolicRate", basalMetabolicRate);
////        redirectAttributes.addAttribute("totalCalories", totalCalories);
//        model.addAttribute("login", login);
//        model.addAttribute("password", password);
//        model.addAttribute("firstName", firstName);
//        model.addAttribute("lastName", lastName);
//        model.addAttribute("age", age);
//        model.addAttribute("sex", sex);
//        model.addAttribute("weight", weight);
//        model.addAttribute("height", height);
//        model.addAttribute("activityLevel", activityLevel);
//        model.addAttribute("basalMetabolicRate", basalMetabolicRate);
//        model.addAttribute("totalCalories", totalCalories);
//        return "redirect:/register/plan";
//    }
//
//    @GetMapping("/register/plan")
//    public String showPlanPage(Model model, @RequestParam(value = "login", required = false) String login, @RequestParam(value = "password", required = false) String password, @RequestParam(value = "firstName", required = false) String firstName, @RequestParam(value = "lastName", required = false) String lastName, @RequestParam(value = "age", required = false) Integer age, @RequestParam(value = "sex", required = false) String sex, @RequestParam(value = "weight", required = false) Float weight, @RequestParam(value = "height", required = false) Float height, @RequestParam(value = "activityLevel", required = false) String activityLevel, @RequestParam(value = "basalMetabolicRate", required = false) Float basalMetabolicRate, @RequestParam(value = "totalCalories", required = false) Float totalCalories) {
//        model.addAttribute("login", login);
//        model.addAttribute("password", password);
//        model.addAttribute("firstName", firstName);
//        model.addAttribute("lastName", lastName);
//        model.addAttribute("age", age);
//        model.addAttribute("sex", sex);
//        model.addAttribute("weight", weight);
//        model.addAttribute("height", height);
//        model.addAttribute("activityLevel", activityLevel);
//        model.addAttribute("basalMetabolicRate", basalMetabolicRate);
//        model.addAttribute("totalCalories", totalCalories);
//        return "plan";
//    }
//
//    @PostMapping("/register/plan")
//    public String processPlanPage(@RequestParam("login") String login, @RequestParam("password") String password, @RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName, @RequestParam("age") int age, @RequestParam("sex") String sex, @RequestParam("activityLevel") String activityLevel, RedirectAttributes redirectAttributes) {
//        redirectAttributes.addAttribute("login", login);
//        redirectAttributes.addAttribute("password", password);
//        redirectAttributes.addAttribute("firstName", firstName);
//        redirectAttributes.addAttribute("lastName", lastName);
//        redirectAttributes.addAttribute("age", age);
//        redirectAttributes.addAttribute("sex", sex);
//        redirectAttributes.addAttribute("activityLevel", activityLevel);
//        return "redirect:/register/completeReg";
//    }
//
//    @GetMapping("/register/completeReg")
//    public String showCompleteRegPage() {
//        return "completeReg";
//    }
//
//    @PostMapping("/register/completeReg+")
//    public String processCompleteRegPage(@RequestParam("login") String login, @RequestParam("password") String password, @RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName, RedirectAttributes redirectAttributes) {
////        User user = new User();
////        user.setLogin(login);
////        user.setPassword(password);
////
////        Person person = new Person();
////        person.setFirstName(firstName);
////        person.setLastName(lastName);
////
////        user.setPerson(person);
////        userRepository.save(user);
////        userService.registerUser(user);
////        заполнить все поля и связанные сущности
//
//        //обращаться ли к отдельному методу запрос-ответ AuthenticationService или сохранить все здесь
//        return "redirect:/login";
//    }
}



/*
import com.makogon.foodtracker.model.User;
import com.makogon.foodtracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

    @Autowired
    private UserRepository userRepository;

    //@Autowired
    private PersonRepository personRepository;

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register/completed")
    public ResponseEntity<AuthenticationResponse> registerUser(@RequestBody RegisterRequest request) {
        // Создание объектов User и Person
        User user = new User();
        user.setLogin(request.getLogin());
        user.setPassword(request.getPassword());

        Person person = new Person();
        person.setFirstName(request.getFirstName());
        person.setLastName(request.getLastName());
        person.setPlanName(request.getPlanName());
        person.setBasePlanID(request.getBasePlanID());

         Связывание объектов User и Person
        user.setPerson(person);
        person.setUser(user);

         Сохранение объектов в базе данных
        userRepository.save(user);
        personRepository.save(person);

        // Выполнение регистрации с использованием AuthenticationService
        AuthenticationResponse response = authenticationService.register(request);

        return ResponseEntity.ok(response);
    }
}
 */