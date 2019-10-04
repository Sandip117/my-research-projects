package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Assignment;
import com.example.demo.model.Course;

public interface AssignmentService {
	void addAssignment(Assignment assignment);
	void updateAssignment(Assignment assignment);
	void deleteAssignment(Assignment assignment);
	void deleteAllAssignments();
	Assignment findAssignmentById(Long assignmentId);
	Course findCourseByAssignment(Long assignmentId);
	List<Assignment> viewAllAssignments();
	List<Assignment> findAssignmentByCourseId(Long courseId);
	
}
