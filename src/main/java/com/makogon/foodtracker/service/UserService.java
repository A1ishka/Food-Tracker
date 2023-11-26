package com.makogon.foodtracker.service;

import com.makogon.foodtracker.model.User;
import com.makogon.foodtracker.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean isLoginUnique(String login) {
        Optional<User> user = userRepository.findByLogin(login);
        return user.isEmpty();
    }

}