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
import com.example.demo.model.CourseRegistration;
import com.example.demo.model.User;
import com.example.demo.service.AssignmentService;
import com.example.demo.service.CourseRegistrationService;
import com.example.demo.service.CourseService;
import com.example.demo.service.UserService;
import com.example.demo.util.CustomErrorType;


@RestController
@RequestMapping("/api")
public class StudentController {

	Logger logger = Logger.getLogger("IController");
	@Autowired
	UserService userService; //Service which will do all data retrieval/manipulation work
	@Autowired
	CourseService courseService;
	@Autowired
	AssignmentService assignmentService;
	@Autowired
	CourseRegistrationService courseRegService;
	
	//----------------------- View all Courses -------------------------------------------
	@RequestMapping(value = "/availableCourses", method = RequestMethod.GET)
	public ResponseEntity<?> getAllCourses() {
		
		List<Course> cList = courseService.viewAllCourses();
		return new ResponseEntity<>(cList,HttpStatus.OK);
	}
	
	//----------------------- Register Courses -------------------------------------------
	@RequestMapping(value = "/registerCourse", method = RequestMethod.POST)
	public ResponseEntity<?> registerCourse(@RequestBody CourseRegistration courseRegistration) {
		logger.info("courseRegistration" + courseRegistration);

		courseRegService.addRegistration(courseRegistration);
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	//----------------------- View Registered Courses ------------------------------------
	@RequestMapping(value = "/registeredCourses", method = RequestMethod.GET)
	public ResponseEntity<?> getRegisteredCourses(@RequestBody Long userId) {
		
		List<Course> cList = courseRegService.viewCoursesByUserId(userId);
		return new ResponseEntity<>(cList,HttpStatus.OK);
	}
	
	//----------------------- Drop Courses -----------------------------------------------
	@RequestMapping(value = "/unregisterCourse", method = RequestMethod.GET)
	public ResponseEntity<?> dropCourse(@RequestParam("courseRegId") long courseRegId) {
		CourseRegistration courseReg = courseRegService.viewCourseRegById(courseRegId);
		courseRegService.dropRegistration(courseReg);
		return new ResponseEntity<>(courseReg , HttpStatus.OK);
	}
	
	
	
}
