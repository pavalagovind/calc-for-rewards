package com.example.service.springbootrewards.rewards;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.service.springbootrewards.model.Customer;

/**
 * Service class for handling rewards business logic.
 * This service acts as an intermediary between the controller and the repository,
 * providing methods to retrieve customer information and calculate reward points.
 * 
 * @author Spring Boot Rewards Team
 * @version 1.0
 */
@Service
public class RewardsService {
	
	/** Repository for accessing customer data */
	@Autowired
	private CustomerRepository customerRepository;
	
	/**
	 * Retrieves all customers from the database.
	 * This method fetches all customer records along with their associated transactions.
	 * 
	 * @return a list of all customers
	 */
	public List<Customer> getCustomerAll() {
		return customerRepository.findAll();
	}
	
	/**
	 * Retrieves a specific customer by their ID.
	 * This method queries the database for a customer with the given ID.
	 * 
	 * @param customerId the ID of the customer to retrieve
	 * @return the Customer object if found, or null if not found
	 */
	public Customer getCustomerById(Integer customerId) {
		return customerRepository.findById(customerId).orElse(null);
	}

}
