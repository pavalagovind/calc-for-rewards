package com.example.service.springbootrewards.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Entity class representing a customer transaction.
 * This class extends Reward to calculate reward points based on the transaction amount.
 * Reward points are calculated as follows:
 * - 2 points for every dollar spent over $100
 * - 1 point for every dollar spent over $50
 * 
 * @author Spring Boot Rewards Team
 * @version 1.0
 */
@Entity
public class CustomerTransaction extends Reward {
	/** Unique identifier for the transaction */
	@Id
	@GeneratedValue
	private Long id;
	
	/** Reference to the customer who made this transaction */
	@JsonIgnore
	@ManyToOne
	@JoinColumn
	private Customer customer;
	
	/** The transaction amount in dollars */
	private Double total;
	
	/** Description of the transaction */
	private String description;
	
	/** The date and time when the transaction was saved */
	@Temporal(TemporalType.TIMESTAMP)
	private Date saveDate;
	
	
	/**
	 * Default constructor for CustomerTransaction.
	 */
	public CustomerTransaction() {
		super();
	}
	
	/**
	 * Constructor to create a CustomerTransaction with all required fields.
	 * 
	 * @param id the transaction ID
	 * @param customer the customer making the transaction
	 * @param total the transaction amount
	 * @param description the transaction description
	 * @param date the transaction date
	 */
	public CustomerTransaction(Long id, Customer customer, Double total, String description, Date date) {
		super();
		this.id = id;
		this.customer = customer;
		this.total = total;
		this.description = description;
		this.saveDate = date;
	}

	/**
	 * Gets the transaction date.
	 * 
	 * @return the transaction date
	 */
	public Date getSaveDate() {
		return saveDate;
	}
	
	/**
	 * Sets the transaction date.
	 * 
	 * @param saveDate the transaction date to set
	 */
	public void setSaveDate(Date saveDate) {
		this.saveDate = saveDate;
	}
	
	/**
	 * Gets the transaction ID.
	 * 
	 * @return the transaction ID
	 */
	public Long getId() {
		return id;
	}
	
	/**
	 * Sets the transaction ID.
	 * 
	 * @param id the transaction ID to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * Gets the customer for this transaction.
	 * 
	 * @return the customer
	 */
	public Customer getCustomer() {
		return customer;
	}
	
	/**
	 * Sets the customer for this transaction.
	 * 
	 * @param customer the customer to set
	 */
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	/**
	 * Gets the transaction amount.
	 * 
	 * @return the transaction total
	 */
	public Double getTotal() {
		return total;
	}
	
	/**
	 * Sets the transaction amount.
	 * 
	 * @param total the transaction total to set
	 */
	public void setTotal(Double total) {
		this.total = total;
	}
	
	/**
	 * Gets the transaction description.
	 * 
	 * @return the transaction description
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * Sets the transaction description.
	 * 
	 * @param description the transaction description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Calculates the reward points for this transaction.
	 * Reward points are calculated as follows:
	 * - 1 point for each dollar spent between $50 and $100
	 * - 2 points for each dollar spent over $100
	 * 
	 * Example: $120 purchase = 1 point for $50 + 2 points * $20 = 90 points
	 * 
	 * @return the calculated reward points
	 */
	@Override
	public Long getPoints() {
		this.points = 0L;
		
		if (this.total > 50 && this.total <= 100) {
			this.points += (this.total.intValue() - 50) * 1;
		} 
		
		if (this.total > 100) {
			this.points += 50;  // 1 point for every dollar spent over $50
			this.points += (this.total.intValue() - 100) * 2;  // 2 points for every dollar spent over $100
		}
		
		return this.points;
	}
	
	/**
	 * Returns a string representation of this transaction.
	 * 
	 * @return a string containing transaction details
	 */
	@Override
	public String toString() {
		return String.format(
			"CustomerTransaction [id=%s, customer=%s, total=%s, description=%s, saveDate=%s]",
			id, customer, total, description, saveDate);
	}
	
}


