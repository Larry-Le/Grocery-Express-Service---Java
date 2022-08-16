package com.gt.express;


import com.gt.express.domain.Role;
import com.gt.express.domain.UserDao;
import com.gt.express.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;


@SpringBootApplication
@EnableFeignClients
@EnableEurekaClient
public class ExpressApplication {

	public static void main(String[] args) {

		SpringApplication.run(ExpressApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	CommandLineRunner run(UserService userService) {
		return args -> {
			userService.saveRole(new Role(null, "ROLE_ADMIN"));
			userService.saveRole(new Role(null, "ROLE_EMPLOYEE"));
			userService.saveRole(new Role(null, "ROLE_CUSTOMER"));
			userService.saveRole(new Role(null, "ROLE_SUPER_ADMIN"));

			userService.saveUser(new UserDao(null, "Leo Li", "leo", "1234", new ArrayList<>()));
			userService.saveUser(new UserDao(null, "Chen Smith", "chen", "1234", new ArrayList<>()));
			userService.saveUser(new UserDao(null, "Jim Lin", "jim", "1234", new ArrayList<>()));
			userService.saveUser(new UserDao(null, "Jing Lang", "Jing", "1234", new ArrayList<>()));

			userService.addRoleToUser("leo", "ROLE_ADMIN");
			userService.addRoleToUser("jim", "ROLE_EMPLOYEE");
			userService.addRoleToUser("chen", "ROLE_CUSTOMER");
			userService.addRoleToUser("Jing", "ROLE_SUPER_ADMIN");
			userService.addRoleToUser("Jing", "ROLE_EMPLOYEE");
			userService.addRoleToUser("Jing", "ROLE_ADMIN");
			userService.addRoleToUser("Jing", "ROLE_CUSTOMER");
		};
	}

}

