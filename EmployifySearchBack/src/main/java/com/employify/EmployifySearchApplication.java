package com.employify;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan()
public class EmployifySearchApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployifySearchApplication.class, args);
	}

	

}
