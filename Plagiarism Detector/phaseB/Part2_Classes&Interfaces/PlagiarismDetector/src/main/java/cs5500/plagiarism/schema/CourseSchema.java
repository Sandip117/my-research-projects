package cs5500.plagiarism.schema;

import cs5500.plagiarism.model.Course;
/**
 * @since 02.10.2018
 * @author team211
 * Operations that can be performed on Course table. 
 */

public interface CourseSchema {
	Course findCourseByUser();
}
