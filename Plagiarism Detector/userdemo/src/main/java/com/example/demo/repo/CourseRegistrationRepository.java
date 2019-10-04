package com.example.demo.repo;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Course;
import com.example.demo.model.CourseRegistration;
import com.example.demo.model.User;
@Repository
public interface CourseRegistrationRepository extends JpaRepository<CourseRegistration,Long> {
	CourseRegistration findCourseRegByRegistrationId(Long registrationId);
	List<Course> findCourseByUserId(Long id);
	List<Course> findCoursesByUserId(Long userId);
	List<User> findStudentsByCourseId(Long courseId);
	List<CourseRegistration> findAllByUserId(Long userId);
	List<CourseRegistration> findAllByCourseId(Long courseId);

}
