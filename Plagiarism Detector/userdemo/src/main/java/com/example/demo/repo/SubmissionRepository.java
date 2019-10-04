package com.example.demo.repo;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Assignment;
import com.example.demo.model.Submission;
import com.example.demo.model.User;


@Repository
public interface SubmissionRepository extends JpaRepository<Submission,Long> {
	Submission findBySubmissionId(Long submissionId);
	User findUserBySubmissionId(Long submissionId);
	Assignment findAssignmentBySubmissionId(Long submissionId);
	List<Submission> findSubmissionsByAssignmentId(Long assignmentId);
	List<Submission> findSubmissionsByUserId(Long userId);
}
