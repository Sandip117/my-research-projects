package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Assignment;
import com.example.demo.model.Submission;
import com.example.demo.model.User;
import com.example.demo.repo.SubmissionRepository;

/**
 * This class implements the SubmissionService
 * 
 * @author sandipsamal
 *
 */
@Service("SubmissionService")
public class SubmissionServiceImpl implements SubmissionService {
	/**
	 * Java Constructor and repository autowired here
	 */
	@Autowired
	SubmissionRepository subRepo;

	SubmissionServiceImpl() {
	};

	public SubmissionServiceImpl(SubmissionRepository subRepo) {
		this.subRepo = subRepo;
	}

	/**
	 * List of public overridden below
	 */
	@Override
	public void addSubmission(Submission submission) {
		subRepo.save(submission);

	}

	@Override
	public void updateSubmission(Submission submission) {
		addSubmission(submission);

	}

	@Override
	public void deleteSubmission(Submission submission) {
		subRepo.delete(submission);

	}

	@Override
	public void deleteAllSubmissions() {
		subRepo.deleteAll();

	}

	@Override
	public Submission findSubmissionById(Long submissionId) {
		return subRepo.findBySubmissionId(submissionId);
	}

	@Override
	public List<Submission> viewAllSubmissions() {
		return subRepo.findAll();
	}

	@Override
	public List<Submission> viewSubmissionsByAssignmentId(Long assignmentId) {
		return subRepo.findSubmissionsByAssignmentId(assignmentId);
	}

	@Override
	public List<Submission> viewSubmissionsByUserId(Long userId) {
		return subRepo.findSubmissionsByUserId(userId);
	}

	@Override
	public Assignment findAssignmentBySubmissionId(Long submissionId) {
		return subRepo.findAssignmentBySubmissionId(submissionId);
	}

	@Override
	public User findUserBySubmissionId(Long submissionId) {
		return subRepo.findUserBySubmissionId(submissionId);
	}

}
