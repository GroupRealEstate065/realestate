package com.hothome;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@EntityScan(basePackages = {"com.hothome.model"})
public class RealestateApplication {
	public static void main(String[] args) {
		SpringApplication.run(RealestateApplication.class, args);
	}
}
