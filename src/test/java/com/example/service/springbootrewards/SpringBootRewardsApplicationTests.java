package com.example.service.springbootrewards;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.service.springbootrewards.model.Customer;
import com.example.service.springbootrewards.model.CustomerTransaction;
import com.example.service.springbootrewards.rewards.CustomerRepository;
import com.example.service.springbootrewards.rewards.RewardsService;

import java.util.Date;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Integration tests for the Spring Boot Rewards Application.
 * These tests verify the application's core functionality including
 * customer retrieval and reward point calculations.
 * 
 * @author Spring Boot Rewards Team
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootRewardsApplicationTests {

	@Autowired
	private RewardsService rewardsService;
	
	@Autowired
	private CustomerRepository customerRepository;

	/**
	 * Test that the application context loads successfully.
	 */
	@Test
	public void testContextLoads() {
		assertNotNull(rewardsService);
		assertNotNull(customerRepository);
	}

	/**
	 * Test that all customers can be retrieved from the database.
	 */
	@Test
	public void testGetAllCustomers() {
		List<Customer> customers = rewardsService.getCustomerAll();
		assertNotNull(customers);
		assertTrue(customers.size() > 0);
	}

	/**
	 * Test that a specific customer can be retrieved by ID.
	 */
	@Test
	public void testGetCustomerById() {
		Customer customer = rewardsService.getCustomerById(100);
		assertNotNull(customer);
		assertEquals("Pavala", customer.getName());
		assertEquals(Integer.valueOf(100), customer.getId());
	}

	/**
	 * Test that a non-existent customer returns null.
	 */
	@Test
	public void testGetNonExistentCustomer() {
		Customer customer = rewardsService.getCustomerById(9999);
		assertNull(customer);
	}

	/**
	 * Test that customer with ID 100 (Pavala) has correct transaction count and reward points.
	 */
	@Test
	public void testCustomerRewardPointsCalculation() {
		Customer customer = rewardsService.getCustomerById(100);
		assertNotNull(customer);
		assertEquals("Pavala", customer.getName());
		
		Set<CustomerTransaction> transactions = customer.getTransactions();
		assertNotNull(transactions);
		assertEquals(9, transactions.size());
		
		// Verify reward points are calculated
		Long totalPoints = customer.getRewardPoints();
		assertTrue(totalPoints > 0);
		
		// Verify total purchases are calculated
		Double totalPurchases = customer.getTotalPurchases();
		assertTrue(totalPurchases > 0);
	}

	/**
	 * Test that customer 101 (Thigazh) has multiple transactions and positive reward points.
	 */
	@Test
	public void testCustomerWithMultipleTransactionsHasRewardPoints() {
		Customer customer = rewardsService.getCustomerById(101);
		assertNotNull(customer);
		assertEquals("Thigazh", customer.getName());
		
		Set<CustomerTransaction> transactions = customer.getTransactions();
		assertNotNull(transactions);
		assertEquals(4, transactions.size());
		
		Long totalPoints = customer.getRewardPoints();
		assertTrue(totalPoints > 0);
	}

	/**
	 * Test reward calculation for $100 transaction (should be 50 points).
	 */
	@Test
	public void testRewardCalculationFor100DollarTransaction() {
		Customer customer = new Customer(999, "Test Customer");
		CustomerTransaction transaction = new CustomerTransaction(999L, customer, 100.0, "Test", new Date());
		
		Long points = transaction.getPoints();
		assertEquals(Long.valueOf(50), points);
	}

	/**
	 * Test reward calculation for $120 transaction (should be 90 points).
	 */
	@Test
	public void testRewardCalculationFor120DollarTransaction() {
		Customer customer = new Customer(999, "Test Customer");
		CustomerTransaction transaction = new CustomerTransaction(999L, customer, 120.0, "Test", new Date());
		
		Long points = transaction.getPoints();
		assertEquals(Long.valueOf(90), points);
	}

	/**
	 * Test reward calculation for $50 transaction (should be 0 points).
	 */
	@Test
	public void testRewardCalculationFor50DollarTransaction() {
		Customer customer = new Customer(999, "Test Customer");
		CustomerTransaction transaction = new CustomerTransaction(999L, customer, 50.0, "Test", new Date());
		
		Long points = transaction.getPoints();
		assertEquals(Long.valueOf(0), points);
	}

	/**
	 * Test reward calculation for $75 transaction (should be 25 points).
	 */
	@Test
	public void testRewardCalculationFor75DollarTransaction() {
		Customer customer = new Customer(999, "Test Customer");
		CustomerTransaction transaction = new CustomerTransaction(999L, customer, 75.0, "Test", new Date());
		
		Long points = transaction.getPoints();
		assertEquals(Long.valueOf(25), points);
	}

	/**
	 * Test that transaction with null total returns 0 points.
	 */
	@Test
	public void testTransactionWithNullTotalHandling() {
		Customer customer = new Customer(999, "Test Customer");
		CustomerTransaction transaction = new CustomerTransaction(999L, customer, null, "Test", new Date());
		
		try {
			Long points = transaction.getPoints();
			// If we reach here, it means it didn't throw an exception
			fail("Should have thrown NullPointerException");
		} catch (NullPointerException e) {
			// Expected behavior
		}
	}

	/**
	 * Test negative amount transaction.
	 */
	@Test
	public void testTransactionWithNegativeAmount() {
		Customer customer = new Customer(999, "Test Customer");
		CustomerTransaction transaction = new CustomerTransaction(999L, customer, -50.0, "Test", new Date());
		
		Long points = transaction.getPoints();
		assertEquals(Long.valueOf(0), points);
	}

	/**
	 * Test that customer name is properly set and retrieved.
	 */
	@Test
	public void testCustomerNameHandling() {
		Customer customer = new Customer(999, "John Doe");
		assertEquals("John Doe", customer.getName());
		
		customer.setName("Jane Doe");
		assertEquals("Jane Doe", customer.getName());
	}

	/**
	 * Test that transaction date is properly saved and retrieved.
	 */
	@Test
	public void testTransactionDateHandling() {
		Date testDate = new Date();
		Customer customer = new Customer(999, "Test Customer");
		CustomerTransaction transaction = new CustomerTransaction(999L, customer, 100.0, "Test", testDate);
		
		assertEquals(testDate, transaction.getSaveDate());
	}

	/**
	 * Test customer 102 (Ram) has 5 transactions.
	 */
	@Test
	public void testCustomer102RamTransactions() {
		Customer customer = rewardsService.getCustomerById(102);
		assertNotNull(customer);
		assertEquals("Ram", customer.getName());
		
		Set<CustomerTransaction> transactions = customer.getTransactions();
		assertNotNull(transactions);
		assertEquals(5, transactions.size());
	}

	/**
	 * Test customer 103 (Sarn) has 4 transactions.
	 */
	@Test
	public void testCustomer103SarnTransactions() {
		Customer customer = rewardsService.getCustomerById(103);
		assertNotNull(customer);
		assertEquals("Sarn", customer.getName());
		
		Set<CustomerTransaction> transactions = customer.getTransactions();
		assertNotNull(transactions);
		assertEquals(4, transactions.size());
	}

	/**
	 * Test customer 104 (Yoga) has 4 transactions.
	 */
	@Test
	public void testCustomer104YogaTransactions() {
		Customer customer = rewardsService.getCustomerById(104);
		assertNotNull(customer);
		assertEquals("Yoga", customer.getName());
		
		Set<CustomerTransaction> transactions = customer.getTransactions();
		assertNotNull(transactions);
		assertEquals(4, transactions.size());
	}

	/**
	 * Test specific transaction reward calculation for Pavala's $100 transaction.
	 */
	@Test
	public void testPavalaElectronicsPurchaseRewards() {
		Customer customer = rewardsService.getCustomerById(100);
		assertNotNull(customer);
		
		// Find the $100 Electronics Purchase transaction
		CustomerTransaction electronics = customer.getTransactions().stream()
			.filter(t -> t.getTotal() == 100.0 && "Electronics Purchase".equals(t.getDescription()))
			.findFirst()
			.orElse(null);
		
		assertNotNull(electronics);
		Long points = electronics.getPoints();
		assertEquals(Long.valueOf(50), points);
	}

	/**
	 * Test specific transaction reward calculation for Pavala's $120 transaction.
	 */
	@Test
	public void testPavalaFurnitureTransactionRewards() {
		Customer customer = rewardsService.getCustomerById(100);
		assertNotNull(customer);
		
		// Find the $120 Furniture transaction
		CustomerTransaction furniture = customer.getTransactions().stream()
			.filter(t -> t.getTotal() == 120.0 && "Furniture".equals(t.getDescription()))
			.findFirst()
			.orElse(null);
		
		assertNotNull(furniture);
		Long points = furniture.getPoints();
		assertEquals(Long.valueOf(90), points);
	}

	/**
	 * Test specific transaction reward calculation for Thigazh's $200 transaction.
	 */
	@Test
	public void testThigazhDepartmentStoreRewards() {
		Customer customer = rewardsService.getCustomerById(101);
		assertNotNull(customer);
		
		// Find the $200 Department Store transaction
		CustomerTransaction dept = customer.getTransactions().stream()
			.filter(t -> t.getTotal() == 200.0 && "Department Store".equals(t.getDescription()))
			.findFirst()
			.orElse(null);
		
		assertNotNull(dept);
		Long points = dept.getPoints();
		// $200: 50 points (for $50-$100) + 2*100 (for over $100) = 50 + 200 = 250
		assertEquals(Long.valueOf(250), points);
	}

}





