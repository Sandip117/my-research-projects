package com.example.demo.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class SubmissionTest {

	@Test
	public void testId() {
		Assignment assgn=new Assignment();
		User student=new User();
		Submission sub=new Submission();
		sub.setSubmissionId(1L);
		sub.setFileName("Sandip");
		sub.setFileLink("src/path");
		sub.setStudent(student);
		sub.setAssignment(assgn);
		assertEquals(1L,sub.getSubmissionId(),0);
	}
	@Test
	public void testName() {
		Assignment assgn=new Assignment();
		User student=new User();
		Submission sub=new Submission();
		sub.setSubmissionId(1L);
		sub.setFileName("Sandip");
		sub.setFileLink("src/path");
		sub.setStudent(student);
		sub.setAssignment(assgn);
		assertEquals("Sandip",sub.getFileName());
	}
	@Test
	public void testLink() {
		Assignment assgn=new Assignment();
		User student=new User();
		Submission sub=new Submission();
		sub.setSubmissionId(1L);
		sub.setFileName("Sandip");
		sub.setFileLink("src/path");
		sub.setStudent(student);
		sub.setAssignment(assgn);
		assertEquals("src/path",sub.getFileLink());
	}
	@Test
	public void testStudent() {
		Assignment assgn=new Assignment();
		User student=new User();
		Submission sub=new Submission();
		sub.setSubmissionId(1L);
		sub.setFileName("Sandip");
		sub.setFileLink("src/path");
		sub.setStudent(student);
		sub.setAssignment(assgn);
		assertEquals(student,sub.getStudent());
	}
	@Test
	public void testAssgn() {
		Assignment assgn=new Assignment();
		User student=new User();
		Submission sub=new Submission();
		sub.setSubmissionId(1L);
		sub.setFileName("Sandip");
		sub.setFileLink("src/path");
		sub.setStudent(student);
		sub.setAssignment(assgn);
		assertEquals(assgn,sub.getAssignment());
	}

}
