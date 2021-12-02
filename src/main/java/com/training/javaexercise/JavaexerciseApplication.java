package com.training.javaexercise;

import com.training.javaexercise.Service.NewsService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class JavaexerciseApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavaexerciseApplication.class, args);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	CommandLineRunner run(NewsService newsService) {
		return args -> {
			/*
			 * SHOULD HAVE PUT SOMETHING IN HERE
			 */
		};
	}
}
