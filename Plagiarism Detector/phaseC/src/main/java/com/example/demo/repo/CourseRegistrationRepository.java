package com.example.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Course;
import com.example.demo.model.CourseRegistration;
import com.example.demo.model.User;

/**
 * This interface represents all the DB methods for CourseRegistration entity
 * 
 * @author sandipsamal
 *
 */
@Repository
public interface CourseRegistrationRepository extends JpaRepository<CourseRegistration, Long> {
	/**
	 * Find CourseRegistration by it id
	 * 
	 * @param registrationId
	 *            : a given registration id
	 * @return : a CourseRegistration for registrationId
	 */
	CourseRegistration findCourseRegByRegistrationId(Long registrationId);

	/**
	 * Find a list of courses by user id
	 * 
	 * @param id
	 *            : a given user id
	 * @return : a list of courses for id
	 */
	List<Course> findCourseByUserId(Long id);

	/**
	 * Find a list of users registered to a course id
	 * 
	 * @param courseId
	 *            : a given course id
	 * @return : a list of users for couresId
	 */
	List<User> findStudentsByCourseId(Long courseId);

	/**
	 * Find a list of course registrations for an user
	 * 
	 * @param userId
	 *            : a given user id
	 * @return : a list of course registrations for userId
	 */
	List<CourseRegistration> findAllByUserId(Long userId);

	/**
	 * Find a list of course registartions by course
	 * 
	 * @param courseId
	 *            : a given id of a course
	 * @return : a list of course registrations having courseId
	 */
	List<CourseRegistration> findAllByCourseId(Long courseId);

}
