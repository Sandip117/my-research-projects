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
public class AssignmentServiceTest {
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
		AssignmentService astests = new AssignmentServiceImpl();
		assignmentService=new AssignmentServiceImpl(aRepository);
		courseService=new CourseServiceImpl(cRepository);
		Course pdp=new Course();
		//courseService.addCourse(pdp);
		entityManager.persist(pdp);
		Assignment assgn=new Assignment();
		assgn.setAssignmentName("Ball-Racket");
		assgn.setCourse(pdp);
		assignmentService.addAssignment(assgn);
		assertThat(assignmentService.viewAllAssignments()).hasSize(1);
		assgn.setAssignmentName("Java");
		assignmentService.updateAssignment(assgn);
		assertThat(assignmentService.viewAllAssignments()).hasSize(1);
		assignmentService.deleteAssignment(assgn);
		assertThat(assignmentService.viewAllAssignments()).hasSize(0);
		assignmentService.deleteAllAssignments();
		assertThat(assignmentService.viewAllAssignments()).hasSize(0);
		
		assignmentService.findAssignmentById(assgn.getAssignmentId());
		assignmentService.findAssignmentByCourseId(pdp.getCourseId());
		assignmentService.findCourseByAssignment(assgn.getAssignmentId());
	}

}
