package com.example.demo.service;
import com.example.demo.model.*;
import com.example.demo.repo.AssignmentRepository;
import com.example.demo.repo.CourseRepository;
import com.example.demo.repo.SemesterRepository;
import com.example.demo.repo.UserRepo;
import com.example.demo.service.UserService;
import com.example.demo.service.UserServiceImpl;

//import static org.assertj.core.api.Assertions.assertThat;

import java.util.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.assertj.core.api.Assertions.assertThat;
 
@RunWith(SpringRunner.class)
@DataJpaTest
public class CourseServiceTest {
//	@Autowired
//	private MockMvc mockMvc;

	@MockBean
	private AssignmentService assignmentService;
	@MockBean
	private CourseService courseService;

	@Autowired
	private TestEntityManager entityManager;
 
	@Autowired
	AssignmentRepository aRepository;
	@Autowired
	CourseRepository cRepository;
	@Test
	public void testUserService() {
		User usr=new User();
		Semester sem=new Semester();
		assignmentService=new AssignmentServiceImpl(aRepository);
		courseService=new CourseServiceImpl(cRepository);
		Course pdp=new Course();
		pdp.setFaculty(usr);
		pdp.setSemester(sem);
		pdp.setCourseName("5010");
		entityManager.persist(usr);
		entityManager.persist(sem);
		
		entityManager.persist(pdp);
		Assignment assgn=new Assignment();
		assgn.setAssignmentName("Ball-Racket");
		assgn.setCourse(pdp);
		assignmentService.addAssignment(assgn);
		entityManager.persist(assgn);
		courseService.addCourse(pdp);
		assertThat(courseService.viewAllCourses()).hasSize(1);
		pdp.setCourseName("5800");
		courseService.updateCourse(pdp);
		assertThat(courseService.viewAllCourses()).hasSize(1);
		assignmentService.deleteAssignment(assgn);
		//courseService.deleteCourse(pdp);
		assertThat(courseService.viewAllCourses()).hasSize(0);
		courseService.deleteAllCourses();
		assertThat(courseService.viewAllCourses()).hasSize(0);
	}

	@Test
	public void testCourseService() {
		CourseService ctest = new CourseServiceImpl();
		User usr = new User();
		Semester sem = new Semester();
		assignmentService=new AssignmentServiceImpl(aRepository);
		courseService = new CourseServiceImpl(cRepository);
		Course cs = new Course();
		cs.setFaculty(usr);
		cs.setSemester(sem);
		cs.setCourseName("computer systems");
		//cs.setCourseId(Long.parseLong("2"));
		courseService.addCourse(cs);
		
		entityManager.persist(usr);
		entityManager.persist(sem);
		entityManager.persist(cs);
		Assignment assgn=new Assignment();
		assgn.setAssignmentName("Ball-Racket");
		assgn.setCourse(cs);
		assignmentService.addAssignment(assgn);
		entityManager.persist(assgn);
		courseService.findCourseById(cs.getCourseId());
		courseService.viewCourseBySemesterId(sem.getSemesterId());
		courseService.viewCoursesByUserId(usr.getId());
		//courseService.findSemesterByCourseId(cs.getCourseId());
		//courseService.findUserByCourseId(cs.getCourseId());
		
		//courseService.deleteCourse(cs);
	}
	
}
