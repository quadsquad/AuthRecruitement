package com.auth.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.client.RestTemplate;

import com.auth.demo.entities.ConfirmationToken;
import com.auth.demo.entities.UserModel;

@SpringBootApplication
@EnableEurekaClient
@CrossOrigin(origins = "https://myworldjob.herokuapp.com")
public class AuthRecruitementApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthRecruitementApplication.class, args);
	}
	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
		
	}
	
	
}
