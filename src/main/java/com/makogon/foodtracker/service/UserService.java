package com.makogon.foodtracker.service;

import com.makogon.foodtracker.model.User;
import com.makogon.foodtracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //    public User getUserById(long userid) {
//        return UserRepository.findById(userid)
//                .orElseThrow(() -> new IllegalArgumentException("Базовый план с идентификатором " + userid + " не найден"));
//    }
    public User getUserByLogin(String login) {
        return userRepository.findByLogin(login)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь с идентификатором " + login + " не найден"));
    }

    public boolean isLoginUnique(String login) {
        Optional<User> user = userRepository.findByLogin(login);
        return user.isEmpty();
    }

    public User getUserByID(Long userID){
        return userRepository.findByUserID(userID)
                .orElseThrow(()->new IllegalArgumentException("Пользователь с идентификатором " + userID + " не найден"));
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }
}