package com.example.demo.service;
import com.example.demo.model.*;
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
public class UserServiceTest {
//	@Autowired
//	private MockMvc mockMvc;

	@MockBean
	private UserService assignmentService;

	@Autowired
	private TestEntityManager entityManager;
 
	@Autowired
	UserRepo repository;
	@Test
	public void testUserService() {
		UserService ustests = new UserServiceImpl();
		assignmentService=new UserServiceImpl(repository);
		User sandip=new User();
		User another=new User();
		//sandip.setId(1L);
		sandip.setEmailId("sandip@husky.neu.edu");
		sandip.setPassword("password");
		sandip.setRole("student");
		User faculty=new User();
		//faculty.setId(2L);
		faculty.setEmailId("faculty@northeastern.edu");
		faculty.setPassword("password");
		faculty.setRole("instructor");
		//entityManager.persist(sandip);
		//entityManager.persist(faculty);
		assignmentService.saveUser(sandip);
		assignmentService.saveUser(faculty);
		
		List<User> users=assignmentService.findAllUsers();
		assertThat(assignmentService.findRoleByEmailId(faculty.getEmailId()).equals("instructor")).isTrue();
		assertThat(assignmentService.findByUsername(faculty.getEmailId()).equals(faculty)).isTrue();
		assertThat(assignmentService.isUserExist(sandip)).isTrue();
		assertThat(assignmentService.isUserExist(another)).isFalse();
		//assertThat(users).hasFieldOrProperty(users.getRole());
		faculty.setPassword("newpassword");
		assignmentService.updateUser(faculty);
		assignmentService.deleteUserById(faculty.getId());
		List<User> updatedUsers=assignmentService.findAllUsers();
		assertThat(updatedUsers).hasSize(1).contains(sandip);
		assignmentService.deleteAllUsers();
		
		assignmentService.findByUsernameAndPassword(sandip.getEmailId(), sandip.getPassword());
		
	}

}
