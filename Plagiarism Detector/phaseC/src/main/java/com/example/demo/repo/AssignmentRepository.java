package com.example.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Assignment;
import com.example.demo.model.Course;

/**
 * This interface represents all the db method for Assignment entity
 * 
 * @author sandipsamal
 *
 */

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
	/**
	 * Finds an assignment by its id
	 * 
	 * @param assignmentId
	 *            : an assignment id
	 * @return : an assignment of id as assignmentId
	 */
	Assignment findByAssignmentId(Long assignmentId);

	/**
	 * Finds course of the assignment id
	 * 
	 * @param assignmentId
	 *            : an assignment id
	 * @return : a course that contains this assignment
	 */
	Course findCourseByAssignmentId(Long assignmentId);

	/**
	 * Finds a list of assignments by course
	 * 
	 * @param course
	 *            : a given Course
	 * @return : a list of assignments for course
	 */
	List<Assignment> findAssignmentsByCourse(Course course);

	/**
	 * Finds a list of assignments by course id
	 * 
	 * @param courseId
	 *            : a given course id
	 * @return : a list of assignmnets for the given courseId
	 */
	List<Assignment> findAssignmentsByCourseId(Long courseId);
	String getLanguageByAssignmentId(Long assignmentId);

}
