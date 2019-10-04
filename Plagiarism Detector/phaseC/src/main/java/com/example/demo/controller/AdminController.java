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
import com.example.demo.model.Semester;
import com.example.demo.model.User;
import com.example.demo.service.AssignmentService;
import com.example.demo.service.CourseService;
import com.example.demo.service.SemesterService;
import com.example.demo.service.UserService;

/**
 * This class represent all the APIs required for the Admin(User) of this system
 * 
 * @author sandipsamal
 *
 */

@RestController
@RequestMapping("/api")
public class AdminController {

	/**
	 * List of services being used declared and autowired below
	 */
	Logger logger = Logger.getLogger("AdminController");
	@Autowired
	UserService userService; // Service which will do all data retrieval/manipulation work
	@Autowired
	CourseService courseService;
	@Autowired
	AssignmentService assignmentService;
	@Autowired
	SemesterService semService;

	/**
	 * Saves a new user to the DB
	 * 
	 * @param user:
	 *            the user json sent from UI to be saved in the system
	 * @return status ok
	 */
	// ------------------ Create Users ------------------------------------
	@RequestMapping(value = "/newUser", method = RequestMethod.POST)
	public ResponseEntity<?> createUser(@RequestBody User user) {
		logger.info("user" + user);

		userService.saveUser(user);

		return new ResponseEntity<String>(HttpStatus.OK);
	}

	/**
	 * Gets all the user in the system
	 * 
	 * @return a list of all users in the system
	 */
	// ------------------ View all Users ----------------------------------
	@RequestMapping(value = "/allusers", method = RequestMethod.GET)
	public ResponseEntity<?> getAllUsers() {

		List<User> uList = userService.findAllUsers();

		return new ResponseEntity<>(uList, HttpStatus.OK);
	}

	/**
	 * Gets a list of users of role "Student" from the DB
	 * 
	 * @return a list of users representing students using the system
	 */
	// ------------------ View all Students ----------------------------------
	@RequestMapping(value = "/allstudents", method = RequestMethod.GET)
	public ResponseEntity<?> getAllStudents() {

		List<User> uList = userService.findAllStudents();

		return new ResponseEntity<>(uList, HttpStatus.OK);
	}

	/**
	 * Gets a list of users of role "Instructor" from the system
	 * 
	 * @return a list of users representing the instructors using the system
	 */
	// ------------------ View all Faculties ----------------------------------
	@RequestMapping(value = "/allfaculties", method = RequestMethod.GET)
	public ResponseEntity<?> getAllFaculties() {

		List<User> uList = userService.findAllFaculties();

		return new ResponseEntity<>(uList, HttpStatus.OK);
	}

	/**
	 * Updates a given user
	 * 
	 * @param user:
	 *            an user that has been updated
	 * @return status OK only iff the updation is successful
	 */

	// ------------------ Update Users/Change roles -----------------------
	@RequestMapping(value = "/updateUser", method = RequestMethod.POST)
	public ResponseEntity<?> updateUser(@RequestBody User user) {
		logger.info("user" + user);

		userService.updateUser(user);

		return new ResponseEntity<String>(HttpStatus.OK);
	}

	/**
	 * Sets the given semester as the current semester of the year
	 * 
	 * @param sem:
	 *            the semester that needs to be set as current semester
	 * @return status OK iff the updation is successful
	 */
	// ------------------ Set as current Semester -----------------------
	@RequestMapping(value = "/currentsemester", method = RequestMethod.POST)
	public ResponseEntity<?> setCurrentSemester(@RequestBody Semester sem) {
		logger.info("semester" + sem);

		semService.setCurrentSemester(sem);

		return new ResponseEntity<String>(HttpStatus.OK);
	}

	/**
	 * Removes a given user from the system DB
	 * 
	 * @param userId
	 *            : the id of the user to be removed
	 * @return status OK iff the deletion is successful
	 */
	// ------------------ Remove user -------------------------------------
	@RequestMapping(value = "/removeUser", method = RequestMethod.DELETE)
	public ResponseEntity<?> removeUser(@RequestParam("userId") long userId) {
		User user = userService.findByUserId(userId);
		userService.deleteUserById(userId);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
}
