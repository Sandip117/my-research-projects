package com.example.demo.controllersTests;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;

import com.example.demo.WebApplication;
import com.example.demo.controller.UserController;
import com.example.demo.model.User;
import com.example.demo.repo.UserRepo;
import com.example.demo.service.UserService;

import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringRunner.class)
@WebMvcTest(value=UserController.class,secure=false)
public class UserControllerTests {
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UserController userController;

//	@Autowired
//	private WebApplicationContext wac;
//
//	@Before
//	public void setup() {
//		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
//
//	}
	private User user;
	private UserRepo userRepo;
	private UserService userService;
	
	@Before
	public void setupMock() {
		user = mock(User.class);
		userRepo = mock(UserRepo.class);
		userService = mock(UserService.class);
	}
	
	@Test
    public void testMockCreation(){
        assertNotNull(user);
        assertNotNull(userRepo);
    }
	
	@Test
	public void findUserTest() {
		User testUser = new User();
		testUser.setId(Long.parseLong("7"));
		testUser.setEmailId("k@husky.neu.edu");
		testUser.setRole("Student");
		testUser.setPassword("123");

		when(userRepo.findByUserId(user.getId())).thenReturn(testUser);
		assertEquals(testUser,userRepo.findByUserId(user.getId()));
		userService.findByUserId(user.getId());
		verify(userRepo).findByUserId(user.getId());
		Mockito.when(userService.findByUserId(Long.parseLong("12"))).thenReturn(testUser);
		Long id = userService.findByUserId(Long.parseLong("12")).getId();
		assertEquals(testUser.getId(),id);
	}
	@Test
	public void createUser() throws Exception {
		
		User testUser = new User();
		testUser.setId(Long.parseLong("7"));
		testUser.setEmailId("k@husky.neu.edu");
		testUser.setRole("Student");
		testUser.setPassword("123");
		
//		when(userService.isUserExist(testUser)).thenReturn(false);
//		doNothing().when(userService).saveUser(anyObject());
		//when(userService.saveUser(anyObject())).thenReturn(testUser);
		
        String testJson = "{\"userId\":\"7\",\"emailId\":\"k@husky.neu.edu\",\"role\":\"Student\",\"password\":\"123\"}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/user").accept(MediaType.APPLICATION_JSON).content(testJson).contentType(MediaType.APPLICATION_JSON);
        
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        String expected = "{role:Student,emailId:k@husky.neu.edu,password:\"123\"}";

        //JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
        
//            
//
//				testUser.setId(Long.parseLong("7"));
//				testUser.setEmailId("k@husky.neu.edu");
//				testUser.setRole("Student");
//				testUser.setPassword("123");
//				Mockito.when(userController.createUser(Mockito.any(User.class)));
				
//		 MvcResult res = mockMvc.perform(MockMvcRequestBuilders.post("api/user")
//				.contentType(MediaType.APPLICATION_JSON)
//				.content("{\"userId\":\"7\""
//						+ ",\"emailId\":\"k@husky.neu.edu\""
//						+ ",\"role\":\"Student\""
//						+ ",\"password\":\"123\"}").accept(MediaType.APPLICATION_JSON))
//		.andReturn();
//		 
//		 System.out.println(res.getResponse().getContentAsString());
	}
	
	@Test
	public void deleteUser() throws Exception {
		User testUser = new User();
		testUser.setId(Long.parseLong("7"));
		testUser.setEmailId("k@husky.neu.edu");
		testUser.setRole("Student");
		testUser.setPassword("123");
		UserService m = mock(UserService.class);
		
		Mockito.when(userController.deleteUser(7)).thenReturn(new ResponseEntity<>(HttpStatus.NO_CONTENT));
		
        String testJson = "{\"emailId\":\"k@husky.neu.edu\",\"role\":\"Student\",\"password\":\"123\"}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/user/7").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);
        
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        String expected = "{role:Student,emailId:k@husky.neu.edu,password:\"123\"}";
        
        //JSONAssert.assertEquals(result.getResponse().getContentAsString(), result.getResponse().getContentAsString(), false);
	}
	
	@Test
	public void findUser() throws Exception {
		User testUser = new User();
		testUser.setId(Long.parseLong("7"));
		testUser.setEmailId("k@husky.neu.edu");
		testUser.setRole("Student");
		testUser.setPassword("123");
		UserService m = mock(UserService.class);	
		
		User u = testUser;
		
        String testJson = "{\"emailId\":\"k@husky.neu.edu\",\"role\":\"Student\",\"password\":\"123\"}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/user/7").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);
        
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        String expected = "{role:Student,emailId:k@husky.neu.edu,password:\"123\"}";
        
        //JSONAssert.assertEquals(result.getResponse().getContentAsString(), result.getResponse().getContentAsString(), false);
	}

	@Test
	public void findUserCred() throws Exception {
		User testUser = new User();
		testUser.setId(Long.parseLong("7"));
		testUser.setEmailId("kaw.s@husky.neu.edu");
		testUser.setRole("Student");
		testUser.setPassword("123");
		UserService m = mock(UserService.class);	
		
		User u = testUser;
		
        String testJson = "{\"emailId\":\"kaw.s@husky.neu.edu\",\"role\":\"Student\",\"password\":\"123\"}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/usercred").accept(MediaType.APPLICATION_JSON).content(testJson).contentType(MediaType.APPLICATION_JSON);
        
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        String expected = "{role:Student,emailId:k@husky.neu.edu,password:\"123\"}";
        
        //JSONAssert.assertEquals(result.getResponse().getContentAsString(), result.getResponse().getContentAsString(), false);
	}
}
