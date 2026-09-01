package com.example.service.springbootrewards.model;

import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Abstract base class representing a reward entity.
 * This class defines the contract for calculating reward points based on transaction data.
 * 
 * @author Spring Boot Rewards Team
 * @version 1.0
 */
public abstract class Reward {

	/**
	 * The reward points calculated for a transaction.
	 * This field is transient and not persisted to the database.
	 */
	@JsonInclude  
	@Transient    
	protected Long points;

	/**
	 * Calculates and returns the reward points for this reward.
	 * 
	 * @return the reward points as a Long value
	 */
	public abstract Long getPoints();

}



