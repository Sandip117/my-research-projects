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
import com.example.demo.model.Assignment;
import com.example.demo.model.Course;
import com.example.demo.model.Semester;
import com.example.demo.service.AssignmentService;
import com.example.demo.service.CourseService;
import com.example.demo.service.SemesterService;
import com.example.demo.service.UserService;
import com.example.demo.service.UserServiceImpl;

/**
 * This class represents a controller for all Instructor(user) related services
 * 
 * @author sandipsamal
 *
 */

@RestController
@RequestMapping("/api")
public class InstructorController {
	/**
	 * list of services to be used are declared and autowired here
	 */
	Logger logger = Logger.getLogger("IController");
	@Autowired
	UserService userService; // Service which will do all data retrieval/manipulation work
	@Autowired
	CourseService courseService;
	@Autowired
	AssignmentService assignmentService;
	@Autowired
	SemesterService semesterService;

	/**
	 * Gets all the semester in the system
	 * 
	 * @returns a list of semesters present in the DB
	 */
	// -------------------get all semesters----------------------------------------

	@RequestMapping(value = "/semesters", method = RequestMethod.GET)
	public ResponseEntity<?> getAllSemesters() {

		List<Semester> cList = semesterService.viewAllSemesters();
		return new ResponseEntity<>(cList, HttpStatus.OK);
	}

	/**
	 * Fetches the semester for the given Id
	 * 
	 * @param sid
	 *            : the semester id for which we want the semester
	 * @return the semester that matches the given semester id
	 */
	// --------------------get specific semester-----------------------------------

	@RequestMapping(value = "/semester", method = RequestMethod.GET)
	public ResponseEntity<?> getSemesterById(@RequestParam("semId") Long sid) {

		Semester sem = semesterService.findSemesterById(sid);
		return new ResponseEntity<>(sem, HttpStatus.OK);
	}

	/**
	 * Fetches all the courses offered in the given semester
	 * 
	 * @param sem
	 *            : a semester
	 * @return a list of courses offered in sem
	 */
	// get all courses
	@RequestMapping(value = "/semester/course", method = RequestMethod.POST)
	public ResponseEntity<?> getCourseBySemester(@RequestBody Semester sem) {

		List<Course> course = courseService.viewCourseBySemesterId(sem.getSemesterId());

		return new ResponseEntity<>(course, HttpStatus.OK);
	}

	/**
	 * Stores a given to the system
	 * 
	 * @param assignment
	 *            an assignment
	 * @return status OK iff the save operation is successful
	 */
	// -------------------add assignment-------------------------------------------

	@RequestMapping(value = "/assignment", method = RequestMethod.POST)
	public ResponseEntity<?> createAssignment(@RequestBody Assignment assignment) {
		logger.info("user" + assignment);

		assignmentService.addAssignment(assignment);

		return new ResponseEntity<String>(HttpStatus.OK);
	}

	/**
	 * Fetches all the assignments for the given course id
	 * 
	 * @param cid
	 *            is the course id
	 * @return a list of assignments present in the course
	 */
	// -------------------get all assignments by course
	// id-------------------------------------------
	@RequestMapping(value = "/assignment", method = RequestMethod.GET)
	public ResponseEntity<?> getAssignmentByCourseId(@RequestParam("courseId") Long cid) {
		logger.info("courseId" + cid);
		List<Assignment> alist = assignmentService.findAssignmentByCourseId(cid);

		return new ResponseEntity<>(alist, HttpStatus.OK);
	}

	/**
	 * Updates an assignment in the system
	 * 
	 * @param a
	 *            is the given assignment
	 * @return status OK iff updation is successful
	 */
	// -------------------update
	// assignment-------------------------------------------
	@RequestMapping(value = "/assignment", method = RequestMethod.PUT)
	public ResponseEntity<?> updateAssignment(@RequestBody Assignment a) {
		logger.info("assignment" + a);
		assignmentService.updateAssignment(a);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	/**
	 * Adds a course to the system
	 * 
	 * @param course
	 *            is a course to be saved
	 * @return status OK if save operation is successful
	 */
	// -------------------add course-------------------------------------------

	@RequestMapping(value = "/course", method = RequestMethod.POST)
	public ResponseEntity<?> createCourse(@RequestBody Course course) {
		logger.info("course" + course);
		courseService.addCourse(course);

		return new ResponseEntity<String>(HttpStatus.OK);
	}

	/**
	 * fetches all the courses taught by the given user
	 * 
	 * @param userId
	 *            is any instructor
	 * @returns a list of courses taught by userId
	 */
	// get all courses
	@RequestMapping(value = "/courses", method = RequestMethod.GET)
	public ResponseEntity<?> getAllCoursesForUser(@RequestParam("userId") Long userId) {

		List<Course> cList = courseService.viewCoursesByUserId(userId);

		return new ResponseEntity<>(cList, HttpStatus.OK);
	}

	/**
	 * fetches a course for a given course id
	 * 
	 * @param courseId
	 *            is the given course id
	 * @return a course from the system that matches courseId
	 */
	// get course by id
	@RequestMapping(value = "/course", method = RequestMethod.GET)
	public ResponseEntity<?> getCourseById(@RequestParam("courseId") long courseId) {

		Course course = courseService.findCourseById(courseId);

		return new ResponseEntity<>(course, HttpStatus.OK);
	}

	/**
	 * fetches an assignment for a given assignmnet id
	 * 
	 * @param assignmentId
	 *            is a given assignment id
	 * @return an assignment from the system that matches assignmentId
	 */
	// get assignment by id
	@RequestMapping(value = "/assignments", method = RequestMethod.GET)
	public ResponseEntity<?> getAssignmentById(@RequestParam("assignmentId") long assignmentId) {

		Assignment assign = assignmentService.findAssignmentById(assignmentId);

		return new ResponseEntity<>(assign, HttpStatus.OK);
	}

	/**
	 * deletes a course
	 * 
	 * @param courseId
	 *            is the given course id for a course that needs to be deleted
	 * @return status OK if deletion is successful
	 */
	// delete course
	@RequestMapping(value = "/course", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteCourse(@RequestParam("courseId") long courseId) {
		Course course = courseService.findCourseById(courseId);
		courseService.deleteCourse(course);
		return new ResponseEntity<>(course, HttpStatus.OK);
	}

	/**
	 * deletes an assignment
	 * 
	 * @param assignmentId
	 *            is the given assignment id for an assignments that needs to be
	 *            deleted
	 * @return status OK iff deletion is successful
	 */
	// delete assignment
	@RequestMapping(value = "/assignment", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteAssignment(@RequestBody Assignment a)//, @RequestParam("courseId") long courseId) {
	{
		assignmentService.deleteAssignment(a);
		return new ResponseEntity<>(HttpStatus.OK);
	}	
}