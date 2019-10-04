package com.example.demo.service;

import java.util.List;

import com.example.demo.model.User;

/**
 * This interface contains a list of user related services provided
 * 
 * @author sandipsamal
 *
 */
public interface UserService {
	/**
	 * Find role of a given username
	 * 
	 * @param username
	 *            : a given user name
	 * @return : a role of the given user name
	 */
	String findRoleByEmailId(String username);

	/**
	 * Find the user by its user name
	 * 
	 * @param username
	 *            : a given username
	 * @return : a user having the given user name
	 */
	User findByUsername(String username);

	/**
	 * Finds a user by its user Id
	 * 
	 * @param userId
	 *            : a given user is
	 * @return : a found user from the system having id as userId
	 */
	User findByUserId(Long userId);

	/**
	 * Adds a new user in the system
	 * 
	 * @param user
	 *            : a given user
	 */
	void saveUser(User user);

	/**
	 * Updates an existing user in the system
	 * 
	 * @param user
	 *            : a given user
	 */

	void updateUser(User user);

	/**
	 * Deletes an existing user from the system
	 * 
	 * @param id
	 *            : a given user id
	 */
	void deleteUserById(Long id);

	/**
	 * Removes all users from the system
	 */
	void deleteAllUsers();

	/**
	 * Finds all users from the system
	 * 
	 * @return : a list of all users
	 */
	List<User> findAllUsers();

	/**
	 * Finds all students from the system
	 * 
	 * @return : a list of users having role="Student"
	 */
	List<User> findAllStudents();

	/**
	 * Finds all faculties in the system
	 * 
	 * @return : a list of users having role="Instructor"
	 */
	List<User> findAllFaculties();

	/**
	 * Checks if an user exists in the system
	 * 
	 * @param user
	 *            : a given user
	 * @return : true iff the given user exists
	 */
	boolean isUserExist(User user);

	/**
	 * Finds a user by its username and password
	 * 
	 * @param username
	 *            : a given username
	 * @param password
	 *            : a given password
	 * @return : a user whose credentials matches username and password
	 */
	User findByUsernameAndPassword(String username, String password);

}
