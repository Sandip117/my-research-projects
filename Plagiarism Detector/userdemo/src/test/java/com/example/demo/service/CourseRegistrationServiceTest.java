package com.example.demo.service;
import com.example.demo.model.*;
import com.example.demo.repo.CourseRegistrationRepository;
import com.example.demo.repo.SubmissionRepository;
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
public class CourseRegistrationServiceTest {
//	@Autowired
//	private MockMvc mockMvc;

	@MockBean
	private CourseRegistrationService crService;

	@Autowired
	private TestEntityManager entityManager;
 
	@Autowired
	CourseRegistrationRepository repository;
	@Test
	public void testUserService() {
		User usr=new User();
		Course course=new Course();
		Assignment as=new Assignment();
		CourseRegistrationService cctests = new CourseRegistrationServiceImpl();
		crService=new CourseRegistrationServiceImpl(repository);
		CourseRegistration cr=new CourseRegistration();
		cr.setCourse(course);
		cr.setStudent(usr);
		entityManager.persist(usr);
		entityManager.persist(course);
		entityManager.persist(cr);
		crService.addRegistration(cr);
		crService.dropRegistration(cr);
		crService.findAllByUserId(usr.getId());
		crService.findAllByCourseId(course.getCourseId());
		crService.viewCourseRegById(cr.getRegistrationId());
		crService.viewStudentsByCourseId(course.getCourseId());
		crService.viewCoursesByUserId(usr.getId());
		
		

		
	}

}
