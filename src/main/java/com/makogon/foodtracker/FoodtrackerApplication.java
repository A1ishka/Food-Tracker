package com.makogon.foodtracker;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.crypto.SecretKey;

@SpringBootApplication
public class FoodtrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(FoodtrackerApplication.class, args);
	}

}
