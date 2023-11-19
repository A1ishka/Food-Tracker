package com.makogon.foodtracker.register;

import com.makogon.foodtracker.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

@Controller
public class RegistrationController {
    private final UserService userService;
    private final UserRepository userRepository;
    public RegistrationController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }
    @GetMapping("/register")
    public String showLoginPage() {
        return "register";
    }
    @PostMapping("/register")
    public String processLoginPage(@RequestParam("login") String login, @RequestParam("password") String password, RedirectAttributes redirectAttributes) {
        if (userService.isLoginUnique(login)) {
            redirectAttributes.addAttribute("login", login);
            redirectAttributes.addAttribute("password", password);
            return "redirect:/register/personal";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Логин уже занят");
            return "redirect:/register";
        }
    }
    @PostMapping("/checkLogin")
    @ResponseBody
    public Map<String, Boolean> checkLoginAvailability(@RequestParam("login") String login) {
        boolean isUnique = userService.isLoginUnique(login);
        Map<String, Boolean> response = new HashMap<>();
        response.put("unique", isUnique);
        return response;
    }

    @GetMapping("/register/personal")
    public String showPersonalPage() {
        return "personal";
    }

    @PostMapping("/register/personal")
    public String processPersonalPage(@RequestParam("login") String login, @RequestParam("password") String password, @RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName, @RequestParam("age") int age,@RequestParam("sex") String sex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("login", login);
        redirectAttributes.addAttribute("password", password);
        redirectAttributes.addAttribute("firstName", firstName);
        redirectAttributes.addAttribute("lastName", lastName);
        redirectAttributes.addAttribute("age", age);
        redirectAttributes.addAttribute("sex", sex);
        return "redirect:/register/calculation";
    }
    @GetMapping("/register/calculation")
    public String showCalculationPage() {
        return "calculation";
    }

    @PostMapping("/register/calculation")
    public String processCalculationPage(@RequestParam("login") String login, @RequestParam("password") String password, @RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName, @RequestParam("age") int age,@RequestParam("sex") String sex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("login", login);
        redirectAttributes.addAttribute("password", password);
        redirectAttributes.addAttribute("firstName", firstName);
        redirectAttributes.addAttribute("lastName", lastName);
        redirectAttributes.addAttribute("age", age);
        redirectAttributes.addAttribute("sex", sex);
        return "redirect:/register/plan";
    }

    @GetMapping("/register/plan")
    public String showPlanPage() {
        return "calculation";
    }

    @PostMapping("/register/plan")
    public String processPlanPage(@RequestParam("login") String login, @RequestParam("password") String password, @RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName, @RequestParam("age") int age,@RequestParam("sex") String sex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("login", login);
        redirectAttributes.addAttribute("password", password);
        redirectAttributes.addAttribute("firstName", firstName);
        redirectAttributes.addAttribute("lastName", lastName);
        redirectAttributes.addAttribute("age", age);
        redirectAttributes.addAttribute("sex", sex);
        //+активность и калораж
        return "redirect:/register/completeReg";
    }
    @GetMapping("/register/complete")
    public String showCompleteRegPage() {
        return "completeReg";
    }

    @PostMapping("/register/complete")
    public String processCompleteRegPage(@RequestParam("login") String login, @RequestParam("password") String password, @RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName, RedirectAttributes redirectAttributes) {
//        User user = new User();
//        user.setLogin(login);
//        user.setPassword(password);
//
//        Person person = new Person();
//        person.setFirstName(firstName);
//        person.setLastName(lastName);
//
//        user.setPerson(person);
//
//        userRepository.save(user);
        //userService.registerUser(user);

        return "redirect:/login";
    }
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