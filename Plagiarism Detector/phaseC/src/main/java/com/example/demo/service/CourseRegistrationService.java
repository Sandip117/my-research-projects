package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Course;
import com.example.demo.model.CourseRegistration;
import com.example.demo.model.User;

/**
 * This interface contains a list of services provided for CourseRegistration
 * 
 * @author sandipsamal
 *
 */
public interface CourseRegistrationService {
	/**
	 * Adds a given courseregistration in the system
	 * 
	 * @param courseRegistration
	 *            : a given course registration
	 */
	void addRegistration(CourseRegistration courseRegistration);

	/**
	 * Removes a course registration
	 * 
	 * @param courseRegistration
	 *            : a given course registration
	 */
	void dropRegistration(CourseRegistration courseRegistration);

	/**
	 * Finds a course registration by its id
	 * 
	 * @param regId
	 *            : a given registration id
	 * @return : a course registration matching the given regId
	 */
	CourseRegistration viewCourseRegById(Long regId);

	/**
	 * Finds a list of users registered to a course
	 * 
	 * @param courseId
	 *            : a given course id
	 * @return : a list of users for the course id
	 */
	List<User> viewStudentsByCourseId(Long courseId);

	/**
	 * Finds a list of courses taken by a user
	 * 
	 * @param userId
	 *            : a given user id
	 * @return : a list of courses for the user id
	 */
	List<Course> viewCoursesByUserId(Long userId);

	/**
	 * Finds a list of course registration by user id
	 * 
	 * @param userId
	 *            : a given user id
	 * @return : a list of course registration for the given userId
	 */
	List<CourseRegistration> findAllByUserId(Long userId);

	/**
	 * Finds a list of course registration by course id
	 * 
	 * @param courseId
	 *            : a given course id
	 * @return : a list of course registartion for the given courseId
	 */
	List<CourseRegistration> findAllByCourseId(Long courseId);
}
