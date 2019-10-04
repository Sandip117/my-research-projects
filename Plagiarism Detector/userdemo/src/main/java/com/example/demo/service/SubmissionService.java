package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Assignment;
import com.example.demo.model.Submission;
import com.example.demo.model.User;

public interface SubmissionService {
	void addSubmission(Submission submission);
	void updateSubmission(Submission submission);
	void deleteSubmission(Submission submission);
	void deleteAllSubmissions();
	Submission findSubmissionById(Long submissionId);
	Assignment findAssignmentBySubmissionId(Long submissionId);
	User findUserBySubmissionId(Long submissionId);
	List<Submission> viewSubmissionsByAssignmentId(Long assignmentId);
	List<Submission> viewSubmissionsByUserId(Long userId);
	List<Submission> viewAllSubmissions();
}
