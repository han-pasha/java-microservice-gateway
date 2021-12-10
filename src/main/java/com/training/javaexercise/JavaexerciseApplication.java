package com.training.javaexercise;

import com.training.javaexercise.Model.Author;
import com.training.javaexercise.Model.News;
import com.training.javaexercise.Model.Role;
import com.training.javaexercise.Model.User;
import com.training.javaexercise.Service.AuthorService;
import com.training.javaexercise.Service.NewsService;
import com.training.javaexercise.Service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.HashSet;

@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker
public class JavaexerciseApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavaexerciseApplication.class, args);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@LoadBalanced
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

	@Bean
	CommandLineRunner run(UserService userService, NewsService newsService, AuthorService authorService) {
		return args -> {
			/*
			 * SHOULD HAVE PUT SOMETHING IN HERE
			 */
			userService.saveUser(new User(null,"han","han",new HashSet<>()));
			userService.saveUser(new User(null,"udin","udin",new HashSet<>()));
			userService.saveRole(new Role(0L,"USER","USER"));
			userService.addRoleToUser("han","USER");

			Author authorDummy = authorService.createAuthor(new Author(null,"Han",new ArrayList<>(),new ArrayList<>()));
			newsService.createNews(new News(null,"Testing",null, authorDummy));
		};
	}
}
