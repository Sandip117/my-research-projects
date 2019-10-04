package com.example.demo.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.demo.model.Assignment;
import com.example.demo.model.Course;
import com.example.demo.model.User;
import com.example.demo.service.AssignmentService;
import com.example.demo.service.CourseService;
import com.example.demo.service.UserService;
import com.example.demo.util.CustomErrorType;


@RestController
@RequestMapping("/api")
public class AdminController {

	Logger logger = Logger.getLogger("AdminController");
	@Autowired
	UserService userService; //Service which will do all data retrieval/manipulation work
	@Autowired
	CourseService courseService;
	@Autowired
	AssignmentService assignmentService;

	//------------------ Create Users ------------------------------------
	@RequestMapping(value = "/newUser", method = RequestMethod.POST)
	public ResponseEntity<?> createUser(@RequestBody User user) {
		logger.info("user" + user);

		userService.saveUser(user);

		return new ResponseEntity<String>(HttpStatus.OK);
	}

	//------------------ View all Users ----------------------------------
	@RequestMapping(value = "/allusers", method = RequestMethod.GET)
	public ResponseEntity<?> getAllUsers() {

		List<User> uList = userService.findAllUsers();

		return new ResponseEntity<>(uList,HttpStatus.OK);
	}

	//------------------ Update Users/Change roles -----------------------
	@RequestMapping(value = "/updateUser", method = RequestMethod.POST)
	public ResponseEntity<?> updateUser(@RequestBody User user) {
		logger.info("user" + user);

		userService.updateUser(user);

		return new ResponseEntity<String>(HttpStatus.OK);
	}

	//------------------ Remove user -------------------------------------
	@RequestMapping(value = "/removeUser", method = RequestMethod.GET)
	public ResponseEntity<?> removeUser(@RequestParam("userId") long userId) {
		User user = userService.findByUserId(userId);
		userService.deleteUserById(userId);
		return new ResponseEntity<>(user , HttpStatus.OK);
	}

}
