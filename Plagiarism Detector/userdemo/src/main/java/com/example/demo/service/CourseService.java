package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Assignment;
import com.example.demo.model.Course;
import com.example.demo.model.Semester;
import com.example.demo.model.User;

public interface CourseService {
	void addCourse(Course course);
	void updateCourse(Course course);
	void deleteCourse(Course course);
	void deleteAllCourses();
	User findUserByCourseId(Long courseId);
	Course findCourseById(Long courseId);
	Semester findSemesterByCourseId(Long courseId);
	List<Course> viewAllCourses();
	List<Course> viewCoursesByUserId(Long userId);
	List<Course> viewCourseBySemesterId(Long semesterId);

	
}
