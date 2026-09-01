package com.example.service.springbootrewards.model;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Entity class representing a customer in the rewards system.
 * Each customer has a unique ID, name, and can have multiple transactions.
 * The class provides methods to calculate total reward points and total purchases.
 * 
 * @author Spring Boot Rewards Team
 * @version 1.0
 */
@Entity
public class Customer {
	/** Unique identifier for the customer */
	@Id
	@GeneratedValue
	private Integer id;
	
	/** The name of the customer */
	private String name;
	
	/** Collection of transactions associated with this customer */
	@OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<CustomerTransaction> transactions;
	
	/** Transient field for total reward points (calculated on demand) */
	@JsonInclude
	@Transient
	private Long rewardPoints;
	
	/** Transient field for total purchase amount (calculated on demand) */
	@JsonInclude
	@Transient
	private Double totalPurchases;
	
	/**
	 * Default constructor for Customer.
	 */
	public Customer() {
		super();
	}
	
	/**
	 * Constructor to create a Customer with ID and name.
	 * 
	 * @param id the customer ID
	 * @param name the customer name
	 */
	public Customer(Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	/**
	 * Gets the customer ID.
	 * 
	 * @return the customer ID
	 */
	public Integer getId() {
		return id;
	}
	
	/**
	 * Sets the customer ID.
	 * 
	 * @param id the customer ID to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	
	/**
	 * Gets the customer name.
	 * 
	 * @return the customer name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the customer name.
	 * 
	 * @param name the customer name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Gets all transactions for this customer.
	 * 
	 * @return a set of customer transactions
	 */
	public Set<CustomerTransaction> getTransactions() {
		return transactions;
	}
	
	/**
	 * Sets the transactions for this customer.
	 * 
	 * @param transactions the set of transactions to set
	 */
	public void setTransactions(Set<CustomerTransaction> transactions) {
		this.transactions = transactions;
	}
	/**
	 * Calculates the total reward points for this customer across all transactions.
	 * 
	 * @return the total reward points, or 0 if no transactions exist
	 */
	public Long getRewardPoints() {
		if (transactions == null || transactions.isEmpty()) {
			return 0L;
		}
		
		return transactions.stream()
			.map(transaction -> transaction.getPoints().intValue())
			.reduce(0, (a, b) -> a + b)
			.longValue();
	}
	
	/**
	 * Calculates the total purchase amount for this customer across all transactions.
	 * 
	 * @return the total purchase amount, or 0.0 if no transactions exist
	 */
	public Double getTotalPurchases() {
		if (transactions == null || transactions.isEmpty()) {
			return 0d;
		}
		
		return transactions.stream()
			.map(transaction -> transaction.getTotal().doubleValue())
			.reduce(0d, (a, b) -> a + b)
			.doubleValue();
	}
	
	
}

