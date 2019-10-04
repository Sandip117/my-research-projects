package com.example.demo.model;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * This test case tests the properties for Semester Model
 * 
 * @author sandipsamal
 *
 */
public class SemesterTests {
	/**
	 * Test for id property
	 */
	@Test
	public void testId() {
		Semester fall18 = new Semester();
		fall18.setSemesterId(1L);
		fall18.setName("Fall 2018");
		assertEquals(1L, fall18.getSemesterId(), 0);
	}

	/**
	 * Test for name property
	 */
	@Test
	public void testName() {
		Semester fall18 = new Semester();
		fall18.setSemesterId(1L);
		fall18.setName("Fall 2018");
		assertEquals("Fall 2018", fall18.getName());
	}

	/**
	 * Test for IsCurrent property
	 */
	@Test
	public void testIsCurr() {
		Semester fall18 = new Semester();
		fall18.setSemesterId(1L);
		fall18.setName("Fall 2018");
		fall18.setIsCurrent(0);
		assertEquals(0, fall18.getIsCurrent());
	}

}
