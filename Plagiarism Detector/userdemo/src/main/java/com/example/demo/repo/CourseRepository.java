package com.example.demo.repo;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Course;
import com.example.demo.model.Semester;
import com.example.demo.model.User;


@Repository
public interface CourseRepository extends JpaRepository<Course,Long> {
	Course findByCourseId(Long courseId);
	Semester findSemesterByCourseId(Long courseId);
	User findUserByCourseId(Long courseId);
	List<Course> findCoursesByFaculty(User faculty);
	List<Course> findCoursesBySemester(Semester semester);
	List<Course> findCoursesByUserId(Long userId);
	List<Course> findCoursesBySemesterId(Long semesterId);
}
