//package com.example.demo;
//import com.example.demo.model.*;
//import com.example.demo.service.UserService;
//import com.example.demo.service.UserServiceImpl;
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
//public class TestUser {
//	@Autowired
//	private TestEntityManager entityManager;
// 
//	@Autowired
//	UserRepo repository;
//	@Test
//	public void testUserService() {
//		UserService us=new UserServiceImpl(repository);
//		User sandip=new User();
//		sandip.setEmailId("sandip@husky.neu.edu");
//		sandip.setPassword("password");
//		sandip.setRole("student");
//		User faculty=new User();
//		faculty.setEmailId("faculty@northeastern.edu");
//		faculty.setPassword("password");
//		faculty.setRole("instructor");
//		us.saveUser(sandip);
//		us.saveUser(faculty);
//		List<User> users=us.findAllUsers();
//		assertThat(users).hasSize(2).contains(sandip,faculty);
//		faculty.setPassword("newpassword");
//		us.updateUser(faculty);
//		us.deleteUserById(faculty.getId());
//		List<User> updatedUsers=us.findAllUsers();
//		assertThat(updatedUsers).hasSize(1).contains(sandip);
//		us.deleteAllUsers();
//		
//	}
//
//}
