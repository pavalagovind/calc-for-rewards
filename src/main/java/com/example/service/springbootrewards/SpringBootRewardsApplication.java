package com.example.service.springbootrewards;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class for the Spring Boot Rewards System.
 * This application implements a rewards program that calculates reward points
 * for customers based on their transaction amounts.
 * 
 * Reward calculation rules:
 * - 2 points for every dollar spent over $100 in each transaction
 * - 1 point for every dollar spent over $50 in each transaction
 * 
 * The application provides REST endpoints to query customer information and rewards data.
 * 
 * @author Spring Boot Rewards Team
 * @version 1.0
 */
@SpringBootApplication
public class SpringBootRewardsApplication {

	/**
	 * Main method to start the Spring Boot application.
	 * 
	 * @param args command-line arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(SpringBootRewardsApplication.class, args);
	}

}



