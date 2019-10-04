package com.example.demo.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class CourseRegisterTest {

	@Test
	public void testId() {
		Course pdp=new Course();
		User student=new User();
		CourseRegistration reg=new CourseRegistration();
		reg.setRegistrationId(1L);
		reg.setCourse(pdp);
		reg.setStudent(student);
		assertEquals(1L,reg.getRegistrationId(),0);
	}
	@Test
	public void testCourse() {
		Course pdp=new Course();
		User student=new User();
		CourseRegistration reg=new CourseRegistration();
		reg.setRegistrationId(1L);
		reg.setCourse(pdp);
		reg.setStudent(student);
		assertEquals(pdp,reg.getCourse());
	}
	@Test
	public void testUser() {
		Course pdp=new Course();
		User student=new User();
		CourseRegistration reg=new CourseRegistration();
		reg.setRegistrationId(1L);
		reg.setCourse(pdp);
		reg.setStudent(student);
		assertEquals(student,reg.getStudent());
	}

}
