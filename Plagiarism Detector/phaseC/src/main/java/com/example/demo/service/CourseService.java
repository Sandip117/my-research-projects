package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Assignment;
import com.example.demo.model.Course;
import com.example.demo.model.Semester;
import com.example.demo.model.User;

/**
 * This interface contains a list of services provided for Course entity
 * 
 * @author sandipsamal
 *
 */
public interface CourseService {
	/**
	 * Adds a course in the system
	 * 
	 * @param course
	 *            : a given course
	 */
	void addCourse(Course course);

	/**
	 * Updates an existing course in the system
	 * 
	 * @param course
	 *            : a given course
	 */
	void updateCourse(Course course);

	/**
	 * Removes a course from the system
	 * 
	 * @param course
	 *            : a given course
	 */
	void deleteCourse(Course course);

	/**
	 * Removes all the courses frm the system
	 */
	void deleteAllCourses();

	/**
	 * Finds a user(faculty) for a course
	 * 
	 * @param courseId
	 *            : a given course id
	 * @return : a user teaching the given course for courseId
	 */
	User findUserByCourseId(Long courseId);

	/**
	 * Finds a course by its id
	 * 
	 * @param courseId
	 *            : a given course id
	 * @return : a course that matches the given courseId
	 */
	Course findCourseById(Long courseId);

	/**
	 * Finds the semester of a course
	 * 
	 * @param courseId
	 *            : a given course id
	 * @return : a semester to which the given course id belongs
	 */
	Semester findSemesterByCourseId(Long courseId);

	/**
	 * Finds a list of all courses in the system
	 * 
	 * @return : a list of all courses
	 */
	List<Course> viewAllCourses();

	/**
	 * Finds a list of courses taught by a user(faculty)
	 * 
	 * @param userId
	 *            : a given user id
	 * @return : a list of courses taught by the user having userId
	 */
	List<Course> viewCoursesByUserId(Long userId);

	/**
	 * Finds a list of all courses offered in a semster
	 * 
	 * @param semesterId
	 *            : a given semester id
	 * @return : a list of courses offered in a semester having id as semesterID
	 */
	List<Course> viewCourseBySemesterId(Long semesterId);

}
