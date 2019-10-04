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
import com.example.demo.model.Semester;
import com.example.demo.model.User;
import com.example.demo.service.AssignmentService;
import com.example.demo.service.CourseRegistrationService;
import com.example.demo.service.CourseService;
import com.example.demo.service.SemesterService;
import com.example.demo.service.UserService;

/**
 * This class represents a controller for all semester related services
 * 
 * @author sandipsamal
 *
 */
@RestController
@RequestMapping("/api")
public class SemesterController {
	/**
	 * list of all services to be used are declared and autowired here
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
	@Autowired
	SemesterService semesterService;
	

	/**
	 * Creates a particular course registration
	 * 
	 * @param course
	 *            the given course registration to be created
	 * @return status OK iff save operation is successful
	 */
	@RequestMapping(value = "/studentcourse", method = RequestMethod.POST)
	public ResponseEntity<?> createCourse(@RequestBody CourseRegistration course) {
		logger.info("course" + course);
		courseRegService.addRegistration(course);

		return new ResponseEntity<String>(HttpStatus.OK);
	}
  
		@RequestMapping(value = "/updateSemester", method = RequestMethod.PUT)
		public ResponseEntity<?> updateSemester(@RequestParam("semesterId") Long semesterId) {

			Semester sem = semesterService.findSemesterById(semesterId);
			List<Semester> semList = semesterService.viewAllSemesters();
			for(Semester s :semList) {
				s.setIsCurrent(0);
			}
			sem.setIsCurrent(1);
			semesterService.updateSemester(sem);
			//System.out.println(courseService.viewAllCourses());
			return new ResponseEntity<>(sem,HttpStatus.OK);
		}

	/**
	 * Fetches all the courses registered to the given user
	 * 
	 * @param userId
	 *            is the user id for an user
	 * @return a list of courses registered usedId
	 */
	// get all courses
	@RequestMapping(value = "/studentcourse", method = RequestMethod.GET)
	public ResponseEntity<?> getAllCoursesForUser(@RequestParam("userId") Long userId) {

		List<Course> cList = courseRegService.viewCoursesByUserId(userId);

		return new ResponseEntity<>(cList, HttpStatus.OK);
	}

	/**
	 * deletes a course registration for the given user id
	 * 
	 * @param regId
	 *            is the given course registration id
	 * @return status OK iff deletion is successful
	 */
	// delete course
	@RequestMapping(value = "/studentcourse", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteCourse(@RequestParam("regId") Long regId) {
		CourseRegistration cr = courseRegService.viewCourseRegById(regId);
		courseRegService.dropRegistration(cr);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
