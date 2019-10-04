package cs5500.plagiarism.model;

import java.util.List;
/**
 * @since 02.10.2018
 * @author team211
 * Faculty role and its privileges
 */
public class Faculty extends User {
	Course course;
	Assignment assign;
	/**
	 * List of courses for an Instructor
	 * @return List of courses.
	 */
	List<Course> getCourseForInstructor(){
		return null;
	}
	/**
	 * List of Assignment for the course
	 * @return assignments
	 */
	List<Assignment> getAssignmentForCourse(){
		return null;
	}
	/**
	 * The plagiarism detection class is invoked here.
	 * @param c1 Course
	 * @param a1 Assignment
	 */
	void runTest(Course c1, Assignment a1){
		
	}
	/**
	 * Instructor adds an assignment
	 * @param a1 Assignment type.
	 */
	void addAssignment(Assignment a1){
		
	}
	/**
	 * History of tests that are run.
	 * @param c1 Course
	 */
	Log viewHistory(Course c1){
		return null;
	}

}
