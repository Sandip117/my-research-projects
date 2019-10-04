package com.example.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Course;
import com.example.demo.model.Semester;
import com.example.demo.model.User;

/**
 * This interface contains all the methods for course entity
 * 
 * @author sandipsamal
 *
 */

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
	/**
	 * Find a course by its id
	 * 
	 * @param courseId
	 *            : a given course id
	 * @return : a course having courseId
	 */
	Course findByCourseId(Long courseId);

	/**
	 * Find the semester of the given course id
	 * 
	 * @param courseId
	 *            : a given a course id
	 * @return : a semester having this course
	 */
	Semester findSemesterByCourseId(Long courseId);

	/**
	 * Find the faculty teaching a course
	 * 
	 * @param courseId
	 *            : a given course id
	 * @return : a user(faculty) teaching a course of courseId
	 */
	User findUserByCourseId(Long courseId);

	/**
	 * Find a list of courses that a faculty teaches
	 * 
	 * @param faculty
	 *            : a user
	 * @return : a list of courses taught by faculty
	 */
	List<Course> findCoursesByFaculty(User faculty);

	/**
	 * Find a list of courses that a semester offers
	 * 
	 * @param semester
	 *            : a given Semester
	 * @return : a list of courses offered in semester
	 */
	/**
	 * Find a list of courses that a faculty teaches
	 * 
	 * @param userId
	 *            : a user
	 * @return : a list of courses taught by faculty having userId
	 */
	List<Course> findCoursesByUserId(Long userId);

	/**
	 * Find a list of courses that a semester offers
	 * 
	 * @param semesterId
	 *            : a given Semester
	 * @return : a list of courses offered in semester having semesterId
	 */
	List<Course> findCoursesBySemesterId(Long semesterId);
}
