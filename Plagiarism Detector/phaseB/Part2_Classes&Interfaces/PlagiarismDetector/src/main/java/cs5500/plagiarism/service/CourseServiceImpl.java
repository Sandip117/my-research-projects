package cs5500.plagiarism.service;

import org.springframework.stereotype.Service;

import cs5500.plagiarism.model.Course;
import cs5500.plagiarism.model.User;
/**
 * @since 02.10.2018
 * @author team211
 * All course related queries. 
 */
@Service("courseService")
public class CourseServiceImpl implements CourseService {

	@Override
	public Course findCourseForUser(User user) {
		return null;
	}

	@Override
	public void saveCourse(Course course) {
		
	}

}
