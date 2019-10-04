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
import com.example.demo.model.User;
import com.example.demo.service.AssignmentService;
import com.example.demo.service.CourseRegistrationService;
import com.example.demo.service.CourseService;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("/api")
public class SemesterController {
	
	Logger logger = Logger.getLogger("IController");
	@Autowired
	UserService userService; //Service which will do all data retrieval/manipulation work
	@Autowired
	CourseService courseService;
	@Autowired
	AssignmentService assignmentService;
	@Autowired
	CourseRegistrationService courseRegService;
	
	@RequestMapping(value = "/studentcourse", method = RequestMethod.POST)
	public ResponseEntity<?> createCourse(@RequestBody CourseRegistration course) {
		logger.info("course" + course);
		courseRegService.addRegistration(course);

		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	// get all courses 
		@RequestMapping(value = "/studentcourse", method = RequestMethod.GET)
		public ResponseEntity<?> getAllCoursesForUser(@RequestParam("userId") Long userId) {

			List<Course> cList = courseRegService.viewCoursesByUserId(userId);

			//System.out.println(courseService.viewAllCourses());
			return new ResponseEntity<>(cList,HttpStatus.OK);
		}
		
		// delete course 
		@RequestMapping(value = "/studentcourse", method = RequestMethod.DELETE)
		public ResponseEntity<?> deleteCourse(@RequestParam("regId") Long regId ) {
			CourseRegistration cr =  courseRegService.viewCourseRegById(regId);
			courseRegService.dropRegistration(cr);
			return new ResponseEntity<>(HttpStatus.OK);
		}

}
