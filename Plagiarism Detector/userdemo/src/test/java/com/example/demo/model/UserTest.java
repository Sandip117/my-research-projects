package com.example.demo.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class UserTest {

	@Test
	public void testId() {
		User sandip =new User();
		sandip.setId(1L);
		sandip.setEmailId("samal.s@husky.neu.edu");
		sandip.setPassword("access");
		sandip.setRole("Student");
		assertEquals(1L,sandip.getId(),0);
	}
	@Test
	public void testEmailId() {
		User sandip =new User();
		sandip.setId(1L);
		sandip.setEmailId("samal.s@husky.neu.edu");
		sandip.setPassword("access");
		sandip.setRole("Student");
		assertEquals("samal.s@husky.neu.edu",sandip.getEmailId());
	}
	@Test
	public void testPassword() {
		User sandip =new User();
		sandip.setId(1L);
		sandip.setEmailId("samal.s@husky.neu.edu");
		sandip.setPassword("access");
		sandip.setRole("Student");
		assertEquals("access",sandip.getPassword());
	}
	@Test
	public void testRole() {
		User sandip =new User();
		sandip.setId(1L);
		sandip.setEmailId("samal.s@husky.neu.edu");
		sandip.setPassword("access");
		sandip.setRole("Student");
		assertEquals("Student",sandip.getRole());
	}

}
