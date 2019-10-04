package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Course;
import com.example.demo.model.CourseRegistration;
import com.example.demo.model.User;
import com.example.demo.repo.CourseRegistrationRepository;

/**
 * This class implements the CourseRegistrationService
 * 
 * @author sandipsamal
 *
 */
@Service("CourseRegistrationService")
public class CourseRegistrationServiceImpl implements CourseRegistrationService {
	/**
	 * Java constructors and repository autowired here
	 */
	@Autowired
	CourseRegistrationRepository courseRegRepo;

	CourseRegistrationServiceImpl() {
	};

	public CourseRegistrationServiceImpl(CourseRegistrationRepository courseRegRepo) {
		this.courseRegRepo = courseRegRepo;
	}

	/**
	 * List of public methods overridden below
	 */
	@Override
	public void addRegistration(CourseRegistration courseRegistration) {
		courseRegRepo.save(courseRegistration);

	}

	@Override
	public void dropRegistration(CourseRegistration courseRegistration) {
		courseRegRepo.delete(courseRegistration);

	}

	@Override
	public List<User> viewStudentsByCourseId(Long courseId) {
		return courseRegRepo.findStudentsByCourseId(courseId);
	}

	@Override
	public List<Course> viewCoursesByUserId(Long userId) {
		return courseRegRepo.findCourseByUserId(userId);
	}

	@Override
	public CourseRegistration viewCourseRegById(Long regId) {
		return courseRegRepo.findCourseRegByRegistrationId(regId);
	}

	@Override
	public List<CourseRegistration> findAllByUserId(Long userId) {
		return courseRegRepo.findAllByUserId(userId);
	}

	@Override
	public List<CourseRegistration> findAllByCourseId(Long courseId) {
		return courseRegRepo.findAllByCourseId(courseId);
	}
}
