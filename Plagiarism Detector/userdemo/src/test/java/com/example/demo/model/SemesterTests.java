package com.example.demo.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class SemesterTests {

	@Test
	public void testId() {
		Semester fall18=new Semester();
		fall18.setSemesterId(1L);
		fall18.setName("Fall 2018");
		assertEquals(1L,fall18.getSemesterId(),0);
	}
	@Test
	public void testName() {
		Semester fall18=new Semester();
		fall18.setSemesterId(1L);
		fall18.setName("Fall 2018");
		assertEquals("Fall 2018",fall18.getName());
	}

}
