package com.makogon.foodtracker.auth;

import com.makogon.foodtracker.model.Person;
import com.makogon.foodtracker.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {
    private String login;
    String password;
//    private Person person;
//    private Role role;
}