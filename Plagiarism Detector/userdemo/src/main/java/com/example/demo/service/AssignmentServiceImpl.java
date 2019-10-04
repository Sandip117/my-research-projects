package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Assignment;
import com.example.demo.repo.AssignmentRepository;
import com.example.demo.model.Course;
import com.example.demo.model.Submission;

@Service("AssignmentService")
public class AssignmentServiceImpl implements AssignmentService {
	@Autowired
	AssignmentRepository assignRepo;
	AssignmentServiceImpl(){};
	public AssignmentServiceImpl(AssignmentRepository assignRepo) {
		this.assignRepo=assignRepo;
	}
	@Override
	public void addAssignment(Assignment assignment) {
		assignRepo.save(assignment);
		
	}

	@Override
	public void updateAssignment(Assignment assignment) {
		addAssignment(assignment);
		
	}

	@Override
	public void deleteAssignment(Assignment assignment) {
		assignRepo.delete(assignment);
		
	}

	@Override
	public void deleteAllAssignments() {
		assignRepo.deleteAll();
		
	}

	@Override
	public Assignment findAssignmentById(Long assignmentId) {
		return assignRepo.findByAssignmentId(assignmentId);
	}
	@Override
	public List<Assignment> viewAllAssignments() {
		return assignRepo.findAll();
	}
	
	@Override
	public Course findCourseByAssignment(Long assignmentId) {
		return assignRepo.findCourseByAssignmentId(assignmentId);
	}
	
	@Override
	public List<Assignment> findAssignmentByCourseId(Long courseId) {
		return assignRepo.findAssignmentsByCourseId(courseId);
	}


}
