package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Assignment;
import com.example.demo.model.Course;

/**
 * this interface contains a list of services provided for assignment
 * 
 * @author sandipsamal
 *
 */
public interface AssignmentService {
	/**
	 * Adds an assignment to the system
	 * 
	 * @param assignment
	 *            : a given assignment
	 */
	void addAssignment(Assignment assignment);

	/**
	 * Updates an existing assignment
	 * 
	 * @param assignment
	 *            : a given assignment
	 */
	void updateAssignment(Assignment assignment);

	/**
	 * Removes an assignment
	 * 
	 * @param assignment
	 *            : a given assignment
	 */
	void deleteAssignment(Assignment assignment);

	/**
	 * Removes all the assignment from the system
	 */
	void deleteAllAssignments();

	/**
	 * Finds an assignment from its id
	 * 
	 * @param assignmentId
	 *            : a given assignment id
	 * @return : a found assignment for the given assignmetId
	 */
	Assignment findAssignmentById(Long assignmentId);

	/**
	 * Finds a course for an assignmengt id
	 * 
	 * @param assignmentId
	 *            : a given assignment id
	 * @return : a course found for the given assignment id
	 */
	Course findCourseByAssignment(Long assignmentId);

	/**
	 * Finds a list of all assignment in the system
	 * 
	 * @return : a list of assignments
	 */
	List<Assignment> viewAllAssignments();

	/**
	 * Finds a list of assignments for a course
	 * 
	 * @param courseId
	 *            : a given course id
	 * @return : a list of all assignments found for courseId
	 */
	List<Assignment> findAssignmentByCourseId(Long courseId);
	String findPreferredLanguageByAssignmentId(Long assignmentId);
}
