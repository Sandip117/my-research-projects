package com.example.demo.repo;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Assignment;
import com.example.demo.model.Course;


@Repository
public interface AssignmentRepository extends JpaRepository<Assignment,Long> {
	Assignment findByAssignmentId(Long assignmentId);
	Course findCourseByAssignmentId(Long assignmentId);
	List<Assignment> findAssignmentsByCourse(Course course);
	List<Assignment> findAssignmentsByCourseId(Long courseId);

}
