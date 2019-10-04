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
import com.example.demo.model.Semester;
import com.example.demo.model.User;
import com.example.demo.service.AssignmentService;
import com.example.demo.service.CourseService;
import com.example.demo.service.SemesterService;
import com.example.demo.service.UserService;
import com.example.demo.service.UserServiceImpl;
import com.example.demo.util.CustomErrorType;


@RestController
@RequestMapping("/api")
public class InstructorController {

	Logger logger = Logger.getLogger("IController");
	@Autowired
	UserService userService; //Service which will do all data retrieval/manipulation work
	@Autowired
	CourseService courseService;
	@Autowired
	AssignmentService assignmentService;
	@Autowired
	SemesterService semesterService;


	//-------------------get all semesters----------------------------------------

	@RequestMapping(value = "/semesters", method = RequestMethod.GET)
	public ResponseEntity<?> getAllSemesters() {


		List<Semester> cList = semesterService.viewAllSemesters();

		//System.out.println(courseService.viewAllCourses());
		return new ResponseEntity<>(cList,HttpStatus.OK);
	}

	//--------------------get specific semester-----------------------------------

	// get all courses 
	@RequestMapping(value = "/semester", method = RequestMethod.GET)
	public ResponseEntity<?> getSemesterById(@RequestParam("semId") Long sid) {

		Semester sem = semesterService.findSemesterById(sid);

		//System.out.println(courseService.viewAllCourses());
		return new ResponseEntity<>(sem,HttpStatus.OK);
	}
	// get all courses 
		@RequestMapping(value = "/semester/course", method = RequestMethod.POST)
		public ResponseEntity<?> getCourseBySemester(@RequestBody Semester sem) {
			
			List<Course> course = courseService.viewCourseBySemesterId(sem.getSemesterId());

			return new ResponseEntity<>(course,HttpStatus.OK);
		}
	// -------------------add assignment-------------------------------------------

	@RequestMapping(value = "/assignment", method = RequestMethod.POST)
	public ResponseEntity<?> createAssignment(@RequestBody Assignment assignment) {
		logger.info("user" + assignment);

		assignmentService.addAssignment(assignment);

		return new ResponseEntity<String>(HttpStatus.OK);
	}
	// -------------------get all assignments by id-------------------------------------------
	@RequestMapping(value = "/assignment", method = RequestMethod.GET)
	public ResponseEntity<?> getAssignmentByCourseId(@RequestParam("courseId") Long cid) {
		logger.info("courseId" + cid);
		//Course c = courseService.findCourseById(cid);
		List<Assignment> alist = assignmentService.findAssignmentByCourseId(cid);

		return new ResponseEntity<>(alist, HttpStatus.OK);
	}
	// -------------------get course by assignment id-------------------------------------------
	@RequestMapping(value = "/assignment", method = RequestMethod.PUT)
	public ResponseEntity<?> updateAssignment(@RequestBody Assignment a) {
		logger.info("assignment" + a);
		assignmentService.updateAssignment(a);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	

	// -------------------add course-------------------------------------------

	@RequestMapping(value = "/course", method = RequestMethod.POST)
	public ResponseEntity<?> createCourse(@RequestBody Course course) {
		logger.info("course" + course);
		courseService.addCourse(course);

		return new ResponseEntity<String>(HttpStatus.OK);
	}

	// get all courses 
	@RequestMapping(value = "/courses", method = RequestMethod.GET)
	public ResponseEntity<?> getAllCoursesForUser(@RequestParam("userId") Long userId) {

		List<Course> cList = courseService.viewCoursesByUserId(userId);

		//System.out.println(courseService.viewAllCourses());
		return new ResponseEntity<>(cList,HttpStatus.OK);
	}
	

	// get course by id 
	@RequestMapping(value = "/course", method = RequestMethod.GET)
	public ResponseEntity<?> getCourseById(@RequestParam("courseId") long courseId) {

		Course course = courseService.findCourseById(courseId);

		return new ResponseEntity<>(course , HttpStatus.OK);
	}

	// get assignment by id 
	@RequestMapping(value = "/assignments", method = RequestMethod.GET)
	public ResponseEntity<?> getAssignmentById(@RequestParam("assignmentId") long assignmentId) {

		Assignment assign = assignmentService.findAssignmentById(assignmentId);

		return new ResponseEntity<>(assign , HttpStatus.OK);
	}


	// delete course 
	@RequestMapping(value = "/course", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteCourse(@RequestParam("courseId") long courseId) {
		Course course = courseService.findCourseById(courseId);
		courseService.deleteCourse(course);
		return new ResponseEntity<>(course , HttpStatus.OK);
	}

	// delete assignment 
	@RequestMapping(value = "/assignment", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteAssignment(@RequestParam("assignmentId") long assignmentId)//, @RequestParam("courseId") long courseId) {
	{Assignment assignment = assignmentService.findAssignmentById(assignmentId);
	//if(assignment.getCourseId()==courseId)
	assignmentService.deleteAssignment(assignment);
	return new ResponseEntity<>(assignment , HttpStatus.OK);
	}	
}