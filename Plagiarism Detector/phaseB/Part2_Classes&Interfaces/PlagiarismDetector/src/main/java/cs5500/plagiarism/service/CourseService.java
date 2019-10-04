package cs5500.plagiarism.service;

import cs5500.plagiarism.model.Course;
import cs5500.plagiarism.model.User;
/**
 * @since 02.10.2018
 * @author team211
 * required functionalities on Courses.
 */
public interface CourseService {
	
	public Course findCourseForUser(User user);
	public void saveCourse(Course course);
	
}
