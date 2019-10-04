package com.example.demo.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class CourseTest {

	@Test
	public void testId() {
		User faculty=new User();
		Semester sem=new Semester();
		Course cs5500=new Course();
		cs5500.setCourseId(1L);
		cs5500.setCourseName("MSD");
		cs5500.setFaculty(faculty);
		cs5500.setSemester(sem);
		assertEquals(1L,cs5500.getCourseId(),0);
}
	@Test
	public void testName() {
		User faculty=new User();
		Semester sem=new Semester();
		Course cs5500=new Course();
		cs5500.setCourseId(1L);
		cs5500.setCourseName("MSD");
		cs5500.setFaculty(faculty);
		cs5500.setSemester(sem);
		assertEquals("MSD",cs5500.getCourseName());
}
	@Test
	public void testFaculty() {
		User faculty=new User();
		Semester sem=new Semester();
		Course cs5500=new Course();
		cs5500.setCourseId(1L);
		cs5500.setCourseName("MSD");
		cs5500.setFaculty(faculty);
		cs5500.setSemester(sem);
		assertEquals(faculty,cs5500.getFaculty());
}
	@Test
	public void testSemester() {
		User faculty=new User();
		Semester sem=new Semester();
		Course cs5500=new Course();
		cs5500.setCourseId(1L);
		cs5500.setCourseName("MSD");
		cs5500.setFaculty(faculty);
		cs5500.setSemester(sem);
		assertEquals(sem,cs5500.getSemester());
}
}
