package com.makogon.foodtracker.user;

import com.makogon.foodtracker.auth.AuthenticationResponse;
import com.makogon.foodtracker.auth.AuthenticationService;
import com.makogon.foodtracker.auth.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {
    /*@GetMapping("/register")
    public ModelAndView showRegisterForm() {
        return new ModelAndView("register");
    }*/

    @GetMapping("/register")
    public String showRegisterForm() {
        return "register";
    }

    @Autowired
    private UserRepository userRepository;

    //@Autowired
    //private PersonRepository personRepository;

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> registerUser(@RequestBody RegisterRequest request) {
        // Создание объектов User и Person
        User user = new User();
        user.setLogin(request.getLogin());
        user.setPassword(request.getPassword());

        /*Person person = new Person();
        person.setFirstName(request.getFirstName());
        person.setLastName(request.getLastName());
        person.setPlanName(request.getPlanName());
        person.setBasePlanID(request.getBasePlanID());*/

        // Связывание объектов User и Person
        //user.setPerson(person);
        //person.setUser(user);

        // Сохранение объектов в базе данных
        userRepository.save(user);
        //personRepository.save(person);

        // Выполнение регистрации с использованием AuthenticationService
        AuthenticationResponse response = authenticationService.register(request);

        return ResponseEntity.ok(response);
    }
}