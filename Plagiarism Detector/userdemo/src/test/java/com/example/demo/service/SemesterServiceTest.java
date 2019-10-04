package com.example.demo.service;
import com.example.demo.model.*;
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
public class SemesterServiceTest {
//	@Autowired
//	private MockMvc mockMvc;

	@MockBean
	private SemesterService semesterService;

	@Autowired
	private TestEntityManager entityManager;
 
	@Autowired
	SemesterRepository repository;
	@Test
	public void testUserService() {
		SemesterService sstests=new SemesterServiceImpl();
		semesterService=new SemesterServiceImpl(repository);
		Semester sem=new Semester();
		sem.setName("Fall 2018");
		Semester sem1=new Semester();
		sem.setName("Spring 2018");
		semesterService.addSemester(sem1);
		semesterService.addSemester(sem);
		assertThat(semesterService.viewAllSemesters()).hasSize(2).contains(sem,sem1);
		assertThat(semesterService.findSemesterById(sem.getSemesterId())).hasFieldOrProperty("name");
		sem.setName("Fall 2019");
		semesterService.updateSemester(sem);
		assertThat(semesterService.findSemesterById(sem.getSemesterId())).hasFieldOrProperty("name");
		semesterService.deleteSemester(sem);
		assertThat(semesterService.viewAllSemesters()).hasSize(1).contains(sem1);
		semesterService.deleteAllSemester();
		assertThat(semesterService.viewAllSemesters()).hasSize(0);
				
		

		
	}

}
