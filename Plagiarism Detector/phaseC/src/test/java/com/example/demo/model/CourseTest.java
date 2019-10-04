package com.example.demo.model;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * This class represents a test case for testing properties of Course Model
 * 
 * @author sandipsamal
 *
 */
public class CourseTest {
	/**
	 * Test for id property
	 */
	@Test
	public void testId() {
		User faculty = new User();
		Semester sem = new Semester();
		Course cs5500 = new Course();
		cs5500.setCourseId(1L);
		cs5500.setCourseName("MSD");
		cs5500.setFaculty(faculty);
		cs5500.setSemester(sem);
		assertEquals(1L, cs5500.getCourseId(), 0);
	}

	/**
	 * Test for name property
	 */
	@Test
	public void testName() {
		User faculty = new User();
		Semester sem = new Semester();
		Course cs5500 = new Course();
		cs5500.setCourseId(1L);
		cs5500.setCourseName("MSD");
		cs5500.setFaculty(faculty);
		cs5500.setSemester(sem);
		assertEquals("MSD", cs5500.getCourseName());
	}

	/**
	 * Test for faculty property
	 */
	@Test
	public void testFaculty() {
		User faculty = new User();
		Semester sem = new Semester();
		Course cs5500 = new Course();
		cs5500.setCourseId(1L);
		cs5500.setCourseName("MSD");
		cs5500.setFaculty(faculty);
		cs5500.setSemester(sem);
		assertEquals(faculty, cs5500.getFaculty());
	}

	/**
	 * Test for semester property
	 */
	@Test
	public void testSemester() {
		User faculty = new User();
		Semester sem = new Semester();
		Course cs5500 = new Course();
		cs5500.setCourseId(1L);
		cs5500.setCourseName("MSD");
		cs5500.setFaculty(faculty);
		cs5500.setSemester(sem);
		assertEquals(sem, cs5500.getSemester());
	}

	/**
	 * Test for course number property
	 */
	@Test
	public void testCourseNum() {
		User faculty = new User();
		Semester sem = new Semester();
		Course cs5500 = new Course();
		cs5500.setCourseId(1L);
		cs5500.setCourseName("MSD");
		cs5500.setFaculty(faculty);
		cs5500.setSemester(sem);
		cs5500.setCourseNumber("cs5500");
		assertEquals("cs5500", cs5500.getCourseNumber());
	}
}
