//package com.example.demo;
//import com.example.demo.model.*;
//import com.example.demo.service.CourseService;
//import com.example.demo.service.CourseServiceImpl;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//import java.util.*;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
//import org.springframework.test.context.junit4.SpringRunner;
// 
// 
//@RunWith(SpringRunner.class)
//@DataJpaTest
//public class TestCourse {
// 
//	@Autowired
//	private TestEntityManager entityManager;
// 
//	@Autowired
//	CourseRepository repository;
//	@Autowired
//	SemesterRepository semRepo;
//	@Test
//	public void should_find_no_customers_if_repository_is_empty() {
//		Semester fall18=new Semester();
//		semRepo.save(fall18);
//		Course course1=new Course();
//		course1.setCourseName("MSD");
//		course1.setSemesterId(fall18.getId());
//		course1.setSemester(fall18);
//		repository.save(course1);
//		assertThat(repository.findAll()).hasSize(1).contains(course1);
//		
//	}
//	@Test
//	public void TestCourseService() {
//		CourseService cs=new CourseServiceImpl(repository);
//		Semester fall18=new Semester();
//		semRepo.save(fall18);
//		Course course1=new Course();
//		course1.setCourseName("MSD");
//		course1.setSemesterId(fall18.getId());
//		course1.setSemester(fall18);
//		cs.addCourse(course1);
//		assertThat(cs.viewAllCourses()).hasSize(1).contains(course1);
//	}
//	
// 
//}
//
