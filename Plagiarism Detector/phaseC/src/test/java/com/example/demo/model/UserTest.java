package com.example.demo.model;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * This test cases tests the properties of User model
 * 
 * @author sandipsamal
 *
 */
public class UserTest {
	/**
	 * Test for id property
	 */
	@Test
	public void testId() {
		User sandip = new User();
		sandip.setId(1L);
		sandip.setEmailId("samal.s@husky.neu.edu");
		sandip.setPassword("access");
		sandip.setRole("Student");
		assertEquals(1L, sandip.getId(), 0);
	}

	/**
	 * Test for email id property
	 */
	@Test
	public void testEmailId() {
		User sandip = new User();
		sandip.setId(1L);
		sandip.setEmailId("samal.s@husky.neu.edu");
		sandip.setPassword("access");
		sandip.setRole("Student");
		assertEquals("samal.s@husky.neu.edu", sandip.getEmailId());
	}

	/**
	 * test for password property
	 */
	@Test
	public void testPassword() {
		User sandip = new User();
		sandip.setId(1L);
		sandip.setEmailId("samal.s@husky.neu.edu");
		sandip.setPassword("access");
		sandip.setRole("Student");
		assertEquals("access", sandip.getPassword());
	}

	/**
	 * test for role property
	 */
	@Test
	public void testRole() {
		User sandip = new User();
		sandip.setId(1L);
		sandip.setEmailId("samal.s@husky.neu.edu");
		sandip.setPassword("access");
		sandip.setRole("Student");
		assertEquals("Student", sandip.getRole());
	}

	/**
	 * test for first name property
	 */
	@Test
	public void testFirstName() {
		User sandip = new User();
		sandip.setFirstName("Sandip");
		sandip.setId(1L);
		sandip.setEmailId("samal.s@husky.neu.edu");
		sandip.setPassword("access");
		sandip.setRole("Student");
		assertEquals("Sandip", sandip.getFirstName());
	}

	/**
	 * Test for last name property
	 */
	@Test
	public void testLastName() {
		User sandip = new User();
		sandip.setId(1L);
		sandip.setLastName("Samal");
		sandip.setEmailId("samal.s@husky.neu.edu");
		sandip.setPassword("access");
		sandip.setRole("Student");
		assertEquals("Samal", sandip.getLastName());
	}

}
