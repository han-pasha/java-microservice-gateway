package com.training.javaexercise;

import com.training.javaexercise.Service.NewsService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class JavaexerciseApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavaexerciseApplication.class, args);
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
