package com.example.demo.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.model.Course;
import com.example.demo.model.CourseRegistration;
import com.example.demo.service.AssignmentService;
import com.example.demo.service.CourseRegistrationService;
import com.example.demo.service.CourseService;
import com.example.demo.service.UserService;

/**
 * This class represents a controller for all the student related services
 * 
 * @author sandipsamal
 *
 */
@RestController
@RequestMapping("/api")
public class StudentController {
	/**
	 * A list of services to be used are declared and autowired here
	 */
	Logger logger = Logger.getLogger("IController");
	@Autowired
	UserService userService; // Service which will do all data retrieval/manipulation work
	@Autowired
	CourseService courseService;
	@Autowired
	AssignmentService assignmentService;
	@Autowired
	CourseRegistrationService courseRegService;

	/**
	 * Fetches all the courses available
	 * 
	 * @return a list of all courses
	 */
	// ----------------------- View all Courses
	// -------------------------------------------
	@RequestMapping(value = "/availableCourses", method = RequestMethod.GET)
	public ResponseEntity<?> getAllCourses() {

		List<Course> cList = courseService.viewAllCourses();
		return new ResponseEntity<>(cList, HttpStatus.OK);
	}

	/**
	 * Registers a course to a student
	 * 
	 * @param courseRegistration
	 *            is a record of course registration
	 * @return status OK iff the save operation is successful
	 */
	// ----------------------- Register Courses
	// -------------------------------------------
	@RequestMapping(value = "/registerCourse", method = RequestMethod.POST)
	public ResponseEntity<?> registerCourse(@RequestBody CourseRegistration courseRegistration) {
		logger.info("courseRegistration" + courseRegistration);

		courseRegService.addRegistration(courseRegistration);
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	/**
	 * Fetches all the courses registered by the student (user)
	 * 
	 * @param userId
	 *            is the user id of the student
	 * @return a list of courses registered by userId
	 */

	// ----------------------- View Registered Courses
	// ------------------------------------
	@RequestMapping(value = "/registeredCourses", method = RequestMethod.GET)
	public ResponseEntity<?> getRegisteredCourses(@RequestBody Long userId) {

		List<Course> cList = courseRegService.viewCoursesByUserId(userId);
		return new ResponseEntity<>(cList, HttpStatus.OK);
	}

	/**
	 * Removes a registration record from the system
	 * 
	 * @param courseRegId
	 *            is a given registration id
	 * @return status OK is deletion is successful
	 */

	// ----------------------- Drop Courses
	// -----------------------------------------------
	@RequestMapping(value = "/unregisterCourse", method = RequestMethod.GET)
	public ResponseEntity<?> dropCourse(@RequestParam("courseRegId") long courseRegId) {
		CourseRegistration courseReg = courseRegService.viewCourseRegById(courseRegId);
		courseRegService.dropRegistration(courseReg);
		return new ResponseEntity<>(courseReg, HttpStatus.OK);
	}

}
