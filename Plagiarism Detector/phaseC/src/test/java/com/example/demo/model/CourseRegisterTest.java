package com.example.demo.model;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * This class represents test cases for the properties of CourseRegistration
 * Model
 * 
 * @author sandipsamal
 *
 */
public class CourseRegisterTest {

	/**
	 * Test for id property
	 */
	@Test
	public void testId() {
		Course pdp = new Course();
		User student = new User();
		CourseRegistration reg = new CourseRegistration();
		reg.setRegistrationId(1L);
		reg.setCourse(pdp);
		reg.setStudent(student);
		assertEquals(1L, reg.getRegistrationId(), 0);
	}

	/**
	 * test for course property
	 */
	@Test
	public void testCourse() {
		Course pdp = new Course();
		User student = new User();
		CourseRegistration reg = new CourseRegistration();
		reg.setRegistrationId(1L);
		reg.setCourse(pdp);
		reg.setStudent(student);
		assertEquals(pdp, reg.getCourse());
	}

	/**
	 * test for user property
	 */
	@Test
	public void testUser() {
		Course pdp = new Course();
		User student = new User();
		CourseRegistration reg = new CourseRegistration();
		reg.setRegistrationId(1L);
		reg.setCourse(pdp);
		reg.setStudent(student);
		assertEquals(student, reg.getStudent());
	}

}
