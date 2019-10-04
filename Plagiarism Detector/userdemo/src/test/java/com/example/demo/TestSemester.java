//package com.example.demo;
//import com.example.demo.model.*;
//import com.example.demo.service.SemesterService;
//import com.example.demo.service.SemesterServiceImpl;
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
//public class TestSemester {
// 
//	@Autowired
//	private TestEntityManager entityManager;
// 
//	@Autowired
//	SemesterRepository repository;
//	@Autowired
//	CourseRepository repository1;
// 
//	@Test
//	public void should_find_no_customers_if_repository_is_empty() {
//		Iterable<Semester> semesters = repository.findAll();
// 
//		assertThat(semesters).isEmpty();
//	}
// 
//	@Test
//	public void should_store_a_User() {
//		Semester fall18=new Semester();
//		fall18.setName("Fall18");
//		Semester spring18=new Semester();
//		spring18.setName("Spring18");
//		entityManager.persist(fall18);
//		repository.save(fall18);
//		repository.save(spring18);
//		Submission sub1=new Submission();
//		Assignment assgn1=new Assignment();
//		Course course1=new Course();
//		course1.setSemesterId(fall18.getId());
//		course1.setSemester(fall18);
//		entityManager.persist(course1);
//		repository1.save(course1);
//		List<Course> courses=new ArrayList<>();
//		courses.add(course1);
//		
//		assertThat(repository.findAll()).hasSize(2).contains(fall18 ,spring18);
//		
//	}
// 
//	@Test
//	public void should_delete_all_customer() {
//		SemesterService ssi=new SemesterServiceImpl(repository);
//		Semester fall18=new Semester();
//		fall18.setName("Fall18");
//		Semester spring18=new Semester();
//		spring18.setName("Spring18");
//		entityManager.persist(fall18);
//		ssi.addSemester(fall18);
//		ssi.addSemester(spring18);
//		Submission sub1=new Submission();
//		Assignment assgn1=new Assignment();
//		Course course1=new Course();
//		course1.setSemesterId(fall18.getId());
//		course1.setSemester(fall18);
//		entityManager.persist(course1);
//		repository1.save(course1);
//		List<Course> courses=new ArrayList<>();
//		courses.add(course1);
//		
//		assertThat(ssi.viewAllSemesters()).hasSize(2).contains(fall18 ,spring18);
//	}
// 
//	@Test
//	public void should_find_all_customers() {
//	}
// 
//	@Test
//	public void should_find_customer_by_id() {
//		
//	}
// 
//}
//
//
