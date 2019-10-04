package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Assignment;
import com.example.demo.model.Course;
import com.example.demo.model.CourseRegistration;
import com.example.demo.repo.AssignmentRepository;
import com.example.demo.repo.CourseRegistrationRepository;
import com.example.demo.repo.CourseRepository;
import com.example.demo.model.Semester;
import com.example.demo.model.User;

@Service("CourseService")
public class CourseServiceImpl implements CourseService {
	@Autowired
	CourseRepository courseRepo;
	@Autowired
	AssignmentRepository assignRepo;
	@Autowired
	CourseRegistrationRepository courseRegRepo;
	CourseServiceImpl(){};
	public CourseServiceImpl(CourseRepository courseRepo) {
		this.courseRepo=courseRepo;
	}
	@Override
	public void addCourse(Course course) {
		courseRepo.save(course);
		
	}

	@Override
	public void updateCourse(Course course) {
		addCourse(course);
		
	}

	@Override
	public void deleteCourse(Course course) {
		List<Assignment> alist=assignRepo.findAssignmentsByCourse(course);
		List<CourseRegistration> clist=courseRegRepo.findAllByCourseId(course.getCourseId());
		for(CourseRegistration cr:clist)
			courseRegRepo.delete(cr);
		for(Assignment a:alist)
			assignRepo.delete(a);
		courseRepo.delete(course);
		
	}

	@Override
	public void deleteAllCourses() {
		courseRepo.deleteAll();
		
	}

	@Override
	public Course findCourseById(Long courseId) {
		return courseRepo.findByCourseId(courseId);
	}

	@Override
	public Semester findSemesterByCourseId(Long courseId) {
		return courseRepo.findSemesterByCourseId(courseId);
	}

	@Override
	public List<Course> viewAllCourses() {
		return courseRepo.findAll();
	}

	@Override
	public User findUserByCourseId(Long courseId) {
		return courseRepo.findUserByCourseId(courseId);
	}
	@Override
	public List<Course> viewCoursesByUserId(Long userId) {
		//return courseRepo.findCoursesByFaculty(faculty);
		return courseRepo.findCoursesByUserId(userId);
	}
	@Override
	public List<Course> viewCourseBySemesterId(Long semesterId) {
		return courseRepo.findCoursesBySemesterId(semesterId);
	}

}
