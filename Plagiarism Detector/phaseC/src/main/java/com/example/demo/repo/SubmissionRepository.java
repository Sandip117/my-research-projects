package com.example.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Assignment;
import com.example.demo.model.Submission;
import com.example.demo.model.User;

/**
 * This interface contains a list of db methods for the Submission entity
 * 
 * @author sandipsamal
 *
 */

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Long> {
	/**
	 * Finds a submission from the db by its id
	 * 
	 * @param submissionIdb:
	 *            a given submission id
	 * @return : a submission whose id is submissionId
	 */
	Submission findBySubmissionId(Long submissionId);

	/**
	 * Finds the user of a given submission id
	 * 
	 * @param submissionId
	 *            : a given submission id
	 * @return : a user that has submitted a submission whose id is submissionId
	 */
	User findUserBySubmissionId(Long submissionId);

	/**
	 * Finds the assignment related to a submission id
	 * 
	 * @param submissionId
	 *            : a given submission id
	 * @return : an assignment which has the submission whose id is submissionId
	 */
	Assignment findAssignmentBySubmissionId(Long submissionId);

	/**
	 * Finds a list of submission submitted for an assignment
	 * 
	 * @param assignmentId
	 *            : a given assignment id
	 * @return : a list of submissions for the given assignmentId
	 */
	List<Submission> findSubmissionsByAssignmentId(Long assignmentId);

	/**
	 * Finds a list of submission submitted by a student
	 * 
	 * @param userId
	 *            : a given user id
	 * @return : a list of submissions made by the user whose id is userId
	 */
	List<Submission> findSubmissionsByUserId(Long userId);
}
