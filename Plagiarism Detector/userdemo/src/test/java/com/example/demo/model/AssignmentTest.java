package com.example.demo.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class AssignmentTest {

	@Test
	public void testId() {
		Course pdp=new Course();
		Assignment assgn1=new Assignment();
		assgn1.setAssignmentId(1L);
		assgn1.setAssignmentName("Racket-Ball");
		assgn1.setCourse(pdp);
		assertEquals(1L,assgn1.getAssignmentId(),0);
	}
	@Test
	public void testName() {
		Course pdp=new Course();
		Assignment assgn1=new Assignment();
		assgn1.setAssignmentId(1L);
		assgn1.setAssignmentName("Racket-Ball");
		assgn1.setCourse(pdp);
		assertEquals("Racket-Ball",assgn1.getAssignmentName());
	}
	@Test
	public void testCourse() {
		Course pdp=new Course();
		Assignment assgn1=new Assignment();
		assgn1.setAssignmentId(1L);
		assgn1.setAssignmentName("Racket-Ball");
		assgn1.setCourse(pdp);
		assertEquals(pdp,assgn1.getCourse());
	}

}
