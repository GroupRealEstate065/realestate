package com.hothome;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

import com.hothome.configuration.SecurityConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class SpringRestDocsConfig {
	public static void main(String[] args) {
		SpringApplication.run(SpringRestDocsConfig.class);
	}
}
