package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Assignment;
import com.example.demo.model.Submission;
import com.example.demo.model.User;

/**
 * This interface contains a list of services provided for Submission entity
 * 
 * @author sandipsamal
 *
 */
public interface SubmissionService {
	/**
	 * Adds a submission in the system
	 * 
	 * @param submission
	 *            : a given submission
	 */
	void addSubmission(Submission submission);

	/**
	 * Updates an existing submission in the system
	 * 
	 * @param submission
	 *            : a given submission
	 */
	void updateSubmission(Submission submission);

	/**
	 * Deletes an existing submission from the system
	 * 
	 * @param submission
	 *            : a given submission
	 */
	void deleteSubmission(Submission submission);

	/**
	 * Removes all the submissions from the system
	 */
	void deleteAllSubmissions();

	/**
	 * Finds a submission by its id
	 * 
	 * @param submissionId
	 *            : a given submission id
	 * @return : a found submission having submissionId as its PK
	 */
	Submission findSubmissionById(Long submissionId);

	/**
	 * Finds the assignment for a submission id
	 * 
	 * @param submissionId
	 *            : a given submission id
	 * @return : an assignment
	 */
	Assignment findAssignmentBySubmissionId(Long submissionId);

	/**
	 * Finds the user of a submission
	 * 
	 * @param submissionId
	 *            : a given submission id
	 * @return : a user for the submission having submissionId
	 */
	User findUserBySubmissionId(Long submissionId);

	/**
	 * Finds a list of submissions by assignment id
	 * 
	 * @param assignmentId
	 *            : a given assignment id
	 * @return : a list of submissions having assignmentId
	 */
	List<Submission> viewSubmissionsByAssignmentId(Long assignmentId);

	/**
	 * Finds a list submission made by a user
	 * 
	 * @param userId
	 *            : an user id
	 * @return : a list of submissions having userId
	 */
	List<Submission> viewSubmissionsByUserId(Long userId);

	/**
	 * Finds all the submissions in the system
	 * 
	 * @return : a list of all submissions
	 */
	List<Submission> viewAllSubmissions();
}
