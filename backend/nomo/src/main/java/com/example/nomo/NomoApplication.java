package com.example.nomo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackages = "com.example.nomo")
public class NomoApplication {

	public static void main(String[] args) {
		SpringApplication.run(NomoApplication.class, args);
	}
}