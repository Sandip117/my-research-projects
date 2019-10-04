package com.example.demo.service;
import com.example.demo.model.*;
import com.example.demo.repo.MossReportRepository;
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
public class MossReportServiceTest {
//	@Autowired
//	private MockMvc mockMvc;

	@MockBean
	private MossReportService mossService;

	@Autowired
	private TestEntityManager entityManager;
 
	@Autowired
	MossReportRepository repository;
	@Test
	public void testUserService() {
		MossReportService sstests=new MossReportServiceImpl();
		mossService=new MossReportServiceImpl(repository);
		Assignment assgn=new Assignment();
		MossReport mr=new MossReport();
		mr.setAssignment(assgn);
		mr.setUser1Id(1L);
		mr.setUser2Id(1L);
		mr.setUser1SubId(1L);
		mr.setUser2SubId(1L);
		mr.setPlagiarismScore(8.9);
		mr.setMossLink("src");
		entityManager.persist(assgn);
		mossService.addReport(mr);
		mr.setPlagiarismScore(89);
		mossService.updateReport(mr);
		MossReport fMr=mossService.viewReportByMossId(mr.getMossId());
		List<MossReport> mList=mossService.viewReportsByAssignmentId(assgn.getAssignmentId());
		mossService.deleteReport(mr);
	
		

		
	}

}
