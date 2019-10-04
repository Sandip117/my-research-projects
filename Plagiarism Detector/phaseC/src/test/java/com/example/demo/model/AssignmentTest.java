package com.example.demo.model;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

/**
 * This class contains test cases for Assignment model
 * 
 * @author sandipsamal
 *
 */
public class AssignmentTest {
	/**
	 * Test for Id property
	 */
	@Test
	public void testId() {
		Course pdp = new Course();
		Assignment assgn1 = new Assignment();
		assgn1.setAssignmentId(1L);
		assgn1.setAssignmentName("Racket-Ball");
		assgn1.setCourse(pdp);
		assertEquals(1L, assgn1.getAssignmentId(), 0);
	}

	/**
	 * Test for name property
	 */
	@Test
	public void testName() {
		Course pdp = new Course();
		Assignment assgn1 = new Assignment();
		assgn1.setAssignmentId(1L);
		assgn1.setAssignmentName("Racket-Ball");
		assgn1.setCourse(pdp);
		assertEquals("Racket-Ball", assgn1.getAssignmentName());
	}

	/**
	 * Test for course property
	 */
	@Test
	public void testCourse() {
		Course pdp = new Course();
		Assignment assgn1 = new Assignment();
		assgn1.setAssignmentId(1L);
		assgn1.setAssignmentName("Racket-Ball");
		assgn1.setCourse(pdp);
		assertEquals(pdp, assgn1.getCourse());
	}

	/**
	 * Test for assignment details property
	 */
	@Test
	public void testAssignmentDtls() {
		Course pdp = new Course();
		Assignment assgn1 = new Assignment();
		assgn1.setAssignmentId(1L);
		assgn1.setAssignmentName("Racket-Ball");
		assgn1.setCourse(pdp);
		assgn1.setAssignmentDetails("Details");
		assertEquals("Details", assgn1.getAssignmentDetails());
	}

	/**
	 * Test for due date property
	 */
	@Test
	public void testDueDate() {
		Course pdp = new Course();
		Date dt = new Date();
		Assignment assgn1 = new Assignment();
		assgn1.setAssignmentId(1L);
		assgn1.setAssignmentName("Racket-Ball");
		assgn1.setCourse(pdp);
		assgn1.setDueDate(dt);
		assertEquals(dt, assgn1.getDueDate());
	}

	/**
	 * Test for preferred language
	 */
	@Test
	public void testLanguage() {
		Course pdp = new Course();
		Date dt = new Date();
		Assignment assgn1 = new Assignment();
		assgn1.setAssignmentId(1L);
		assgn1.setAssignmentName("Racket-Ball");
		assgn1.setCourse(pdp);
		assgn1.setDueDate(dt);
		assgn1.setlanguage("Java");
		assertEquals("Java",assgn1.getlanguage());
	}

}
