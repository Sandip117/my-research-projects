package com.example.demo.model;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

/**
 * This test case tests properties of Submission Model
 * 
 * @author sandipsamal
 *
 */
public class SubmissionTest {
	/**
	 * Test for id property
	 */
	@Test
	public void testId() {
		Assignment assgn = new Assignment();
		User student = new User();
		Submission sub = new Submission();
		sub.setSubmissionId(1L);
		sub.setFileName("Sandip");
		sub.setFileLink("src/path");
		sub.setStudent(student);
		sub.setAssignment(assgn);
		assertEquals(1L, sub.getSubmissionId(), 0);
	}

	/**
	 * Test for file name property
	 */
	@Test
	public void testName() {
		Assignment assgn = new Assignment();
		User student = new User();
		Submission sub = new Submission();
		sub.setSubmissionId(1L);
		sub.setFileName("Sandip");
		sub.setFileLink("src/path");
		sub.setStudent(student);
		sub.setAssignment(assgn);
		assertEquals("Sandip", sub.getFileName());
	}

	/**
	 * test for file link property
	 */
	@Test
	public void testLink() {
		Assignment assgn = new Assignment();
		User student = new User();
		Submission sub = new Submission();
		sub.setSubmissionId(1L);
		sub.setFileName("Sandip");
		sub.setFileLink("src/path");
		sub.setStudent(student);
		sub.setAssignment(assgn);
		assertEquals("src/path", sub.getFileLink());
	}

	/**
	 * Test for student property
	 */
	@Test
	public void testStudent() {
		Assignment assgn = new Assignment();
		User student = new User();
		Submission sub = new Submission();
		sub.setSubmissionId(1L);
		sub.setFileName("Sandip");
		sub.setFileLink("src/path");
		sub.setStudent(student);
		sub.setAssignment(assgn);
		assertEquals(student, sub.getStudent());
	}

	/**
	 * Test for assignment property
	 */
	@Test
	public void testAssgn() {
		Assignment assgn = new Assignment();
		User student = new User();
		Submission sub = new Submission();
		sub.setSubmissionId(1L);
		sub.setFileName("Sandip");
		sub.setFileLink("src/path");
		sub.setStudent(student);
		sub.setAssignment(assgn);
		assertEquals(assgn, sub.getAssignment());
	}

	/**
	 * test for submission date property
	 */
	@Test
	public void testSubmissionDate() {
		Assignment assgn = new Assignment();
		User student = new User();
		Submission sub = new Submission();
		sub.setSubmissionId(1L);
		sub.setFileName("Sandip");
		sub.setFileLink("src/path");
		sub.setStudent(student);
		sub.setAssignment(assgn);
		Date dt = new Date();
		sub.setSubmissionDate(dt);
		assertEquals(dt, sub.getSubmissionDate());
	}

}
