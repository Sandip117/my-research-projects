//package com.example.demo;
//import com.example.demo.model.*;
//import com.example.demo.service.AssignmentService;
//import com.example.demo.service.AssignmentServiceImpl;
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
// 
// 
//@RunWith(SpringRunner.class)
//@DataJpaTest
//public class TestAssignment {
// 
//	@Autowired
//	private TestEntityManager entityManager;
// 
//	@Autowired
//	CourseRepository repository;
//	@Autowired
//	SemesterRepository semRepo;
//	@Autowired
//	AssignmentRepository assgRepo;
//	@Test
//	public void should_find_no_customers_if_repository_is_empty() {
//		Semester fall18=new Semester();
//		semRepo.save(fall18);
//		Course course1=new Course();
//		course1.setCourseName("MSD");
//		course1.setSemesterId(fall18.getId());
//		course1.setSemester(fall18);
//		repository.save(course1);
//		Assignment assgn=new Assignment();
//		assgn.setCourse(course1);
//		assgn.setCourseId(course1.getCourseId());
//		assgRepo.save(assgn);
//		assertThat(assgRepo.findAll()).hasSize(1).contains(assgn);
//		
//	}
//	@Test
//	public void TestAssignmentServices() {
//		AssignmentService as=new AssignmentServiceImpl(assgRepo);
//		Semester fall18=new Semester();
//		semRepo.save(fall18);
//		Course course1=new Course();
//		course1.setCourseName("MSD");
//		course1.setSemesterId(fall18.getId());
//		course1.setSemester(fall18);
//		repository.save(course1);
//		Assignment assgn=new Assignment();
//		assgn.setCourse(course1);
//		assgn.setCourseId(course1.getCourseId());
//		as.addAssignment(assgn);
//		assertThat(as.viewAllAssignments()).hasSize(1).contains(assgn);
//	}
// 
//	
// 
//}
//
//
