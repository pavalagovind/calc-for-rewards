package com.example.service.springbootrewards.rewards;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.example.service.springbootrewards.model.Customer;

/**
 * REST Controller for managing rewards-related endpoints.
 * This controller exposes HTTP endpoints for querying customer information and rewards data.
 * 
 * @author Spring Boot Rewards Team
 * @version 1.0
 */
@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class RewardsController {

	/** Service for handling rewards business logic */
	@Autowired
	private RewardsService rewardsService;
	
	/**
	 * Retrieves all customers from the system.
	 * 
	 * @return a list of all customers
	 */
	@GetMapping("/customers")
	public List<Customer> findCustomerAll() {
		return rewardsService.getCustomerAll();
	}
	
	/**
	 * Retrieves a specific customer by ID.
	 * 
	 * @param id the customer ID
	 * @return ResponseEntity containing the customer if found, or NOT_FOUND status if not found
	 */
	@GetMapping("/customers/{id}")
	public ResponseEntity<Customer> getCustomer(@PathVariable Integer id) {
		Customer customer = rewardsService.getCustomerById(id);
		if (customer == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(customer, HttpStatus.OK);
	}
	
	
}




