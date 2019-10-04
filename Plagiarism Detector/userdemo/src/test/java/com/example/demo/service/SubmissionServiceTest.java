package com.example.demo.service;
import com.example.demo.model.*;
import com.example.demo.repo.SubmissionRepository;
import com.example.demo.repo.UserRepo;
import com.example.demo.service.UserService;
import com.example.demo.service.UserServiceImpl;
import static org.junit.Assert.*;
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
public class SubmissionServiceTest {
//	@Autowired
//	private MockMvc mockMvc;

	@MockBean
	private SubmissionService subService;
	private AssignmentService aSer;
	
	@Autowired(required=true)
	private TestEntityManager entityManager;
 
	@Autowired(required=true)
	SubmissionRepository repository;
	@Test
	public void testUserService() {
		User usr=new User();
		SubmissionService sstest = new SubmissionServiceImpl();
		Assignment as=new Assignment();
		
		entityManager.persist(usr);
		entityManager.persist(as);
		subService=new SubmissionServiceImpl(repository);
		Submission sub=new Submission();
		sub.setAssignment(as);
		sub.setStudent(usr);
		sub.setFileName("sandip");
		sub.setFileLink("src");
		subService.addSubmission(sub);
		entityManager.persist(sub);
		entityManager.persist(as);
		assertThat(subService.viewAllSubmissions()).hasSize(1).contains(sub);
		sub.setFileName("sandip1");
		subService.updateSubmission(sub);
		//assertEquals(subService.findAssignmentBySubmissionId(sub.getSubmissionId()),as);
		//assertThat(subService.findAssignmentBySubmissionId(sub.getSubmissionId())).hasFieldOrProperty("name");
		List<Submission> slist=subService.viewAllSubmissions();
		Submission fsub=subService.findSubmissionById(sub.getSubmissionId());
		subService.deleteSubmission(sub);
		assertThat(subService.viewAllSubmissions()).hasSize(0);
		subService.deleteAllSubmissions();
		assertThat(subService.viewAllSubmissions()).hasSize(0);
		subService.findAssignmentBySubmissionId(sub.getSubmissionId());
		subService.findUserBySubmissionId(sub.getSubmissionId());
		subService.findSubmissionById(sub.getSubmissionId());
		
		subService.viewSubmissionsByUserId(usr.getId());
		//subService.viewSubmissionsByAssignmentId(as.getAssignmentId());
		
	}

}
