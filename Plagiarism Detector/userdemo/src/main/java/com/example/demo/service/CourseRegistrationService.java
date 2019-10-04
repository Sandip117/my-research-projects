package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Course;
import com.example.demo.model.CourseRegistration;
import com.example.demo.model.User;

public interface CourseRegistrationService {
	void addRegistration(CourseRegistration courseRegistration);
	void dropRegistration(CourseRegistration courseRegistration);
	CourseRegistration viewCourseRegById(Long regId);
	List<User> viewStudentsByCourseId(Long courseId);
	List<Course> viewCoursesByUserId(Long userId);
	List<CourseRegistration> findAllByUserId(Long userId);
	List<CourseRegistration> findAllByCourseId(Long courseId);
}
