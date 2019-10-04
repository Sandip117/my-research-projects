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

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import com.example.demo.util.CustomErrorType;


@RestController
@RequestMapping("/api")
public class UserController {

	Logger logger = Logger.getLogger("UserController");
	@Autowired
	UserService userService; //Service which will do all data retrieval/manipulation work

	@RequestMapping(value = "/usercred", method = RequestMethod.GET)
	public ResponseEntity<?> getUserByCredentials(@RequestParam("emailId") String emailId, @RequestParam("password") String password) {
		User user = userService.findByUsernameAndPassword(emailId, password);
		if (user == null) {
			return new ResponseEntity(new CustomErrorType("User with id " + emailId 
					+ " not found"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
	// -------------------Retrieve All Users---------------------------------------------

	@RequestMapping(value = "/user/", method = RequestMethod.GET)
	public ResponseEntity<List<User>> listAllUsers() {
		List<User> users = userService.findAllUsers();
		if (users.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}
	
	// -------------------Retrieve Single User------------------------------------------

	@RequestMapping(value = "/user/{user_id}", method = RequestMethod.GET)
	public ResponseEntity<?> getUser(@PathVariable("user_id") long userId) {
		User user = userService.findByUserId(userId);
		if (user == null) {
			return new ResponseEntity(new CustomErrorType("User with id " + userId 
					+ " not found"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	// -------------------Retrieve Single User by Username------------------------------------------

	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public ResponseEntity<?> getUserByEmailId(@RequestParam("emailId") String emailId) {
		User user = userService.findByUsername(emailId);

		if (user == null) {
			return new ResponseEntity(new CustomErrorType("User with id " + emailId 
					+ " not found"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}


	// -------------------Create a User-------------------------------------------

	@RequestMapping(value = "/user", method = RequestMethod.POST)
	public ResponseEntity<?> createUser(@RequestBody User user) {
		logger.info("user" + user);

		if (userService.isUserExist(user)) {
			return new ResponseEntity(new CustomErrorType("Unable to create. A User with name " + 
					user.getEmailId() + " already exist."),HttpStatus.CONFLICT);
		}
		userService.saveUser(user);

		  // HttpHeaders headers = new HttpHeaders();
		//headers.setLocation(ucBuilder.path("/api/user/{id}").buildAndExpand(user.getId()).toUri());
		return new ResponseEntity<User>(user,HttpStatus.OK);
	}

	// ------------------- Update a User ------------------------------------------------

	@RequestMapping(value = "/user/{user_id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateUser(@PathVariable("user_id") long userId, @RequestBody User user) {

		User currentUser = userService.findByUserId(userId);

		if (currentUser == null) {
			return new ResponseEntity(new CustomErrorType("Unable to upate. User with id " + userId + " not found."),
					HttpStatus.NOT_FOUND);
		}

		currentUser.setEmailId(user.getEmailId());

		userService.updateUser(currentUser);
		return new ResponseEntity<User>(currentUser, HttpStatus.OK);
	}

	// ------------------- Delete a User-----------------------------------------

	@RequestMapping(value = "/user/{user_id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteUser(@PathVariable("user_id") long userId) {

		User user = userService.findByUserId(userId);
		if (user == null) {
			return new ResponseEntity(new CustomErrorType("Unable to delete. User with id " + userId + " not found."),
					HttpStatus.NOT_FOUND);
		}
		userService.deleteUserById(userId);
		return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
	}

	// ------------------- Delete All Users-----------------------------

	@RequestMapping(value = "/user/", method = RequestMethod.DELETE)
	public ResponseEntity<User> deleteAllUsers() {

		userService.deleteAllUsers();
		return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
	}
	@RequestMapping(value = "/logout", method = RequestMethod.DELETE)
	public ResponseEntity<?> logout() {

		return new ResponseEntity<User>(HttpStatus.OK);
	}
	
}