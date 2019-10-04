package com.example.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.User;

/**
 * this interface conatins a list of db methods for the user entity
 * 
 * @author sandipsamal
 *
 */
@Repository
public interface UserRepo extends JpaRepository<User, Long> {
	/**
	 * Finds a user by its id
	 * 
	 * @param userId
	 *            : a given user id
	 * @return : a user whose id is userId
	 */
	User findByUserId(Long userId);

	/**
	 * Finds a user by its email Id
	 * 
	 * @param email
	 *            : a given email id
	 * @return : a user whose email id is emailId
	 */
	User findByEmailId(String email);

	/**
	 * Finds a user by its email is and password
	 * 
	 * @param email
	 *            : a given email id
	 * @param password
	 *            : a password
	 * @return : a user whose email id and password matches to the given credentials
	 */
	User findByEmailIdAndPassword(String email, String password);

	/**
	 * Finds a list of users of the given role
	 * 
	 * @param role
	 *            : a given user role
	 * @return : a list of users having role same as the given role
	 */
	List<User> findByRole(String role);
}