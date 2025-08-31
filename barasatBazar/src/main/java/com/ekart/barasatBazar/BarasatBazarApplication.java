package com.ekart.barasatBazar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.ekart.*"})
public class BarasatBazarApplication {

	public static void main(String[] args) {
		SpringApplication.run(BarasatBazarApplication.class, args);
	}

}
