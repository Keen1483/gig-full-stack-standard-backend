package com.devskills.gigfullstackstandard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/*
import org.springframework.context.annotation.Bean;
import org.springframework.boot.CommandLineRunner;
import com.devskills.gigfullstackstandard.model.User;
import com.devskills.gigfullstackstandard.service.UserService;
import com.github.javafaker.Faker; */

@SpringBootApplication
public class GigFullstackStandardApplication {

	public static void main(String[] args) {
		SpringApplication.run(GigFullstackStandardApplication.class, args);
	}
	/*
	@Bean
	CommandLineRunner run(UserService userService) {
		return args -> {
			Faker faker = new Faker();
			for (int i = 0; i < 100; i++) {
				userService.saveUser(new User(
						null,
						faker.internet().emailAddress(),
						faker.name().username(),
						"1234",
						null,
						null,
						null
					)
				);
			}
		};
	} */

}
