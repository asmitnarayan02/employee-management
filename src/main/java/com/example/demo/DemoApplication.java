package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
		// Starts the Spring Boot application.
        // It performs the following tasks:
        // 1. Creates the Spring Application Context.
        // 2. Scans for components (@Controller, @Service, @Repository, etc.).
        // 3. Applies auto-configuration.
        // 4. Starts the embedded Tomcat server.
        // 5. Makes the application ready to accept HTTP requests.
	}

}