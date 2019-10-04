package cs5500.plagiarism.model;

import java.util.List;

public class Student extends Role {

	String studentId;
	String studentName;
	List<String> courses;
	List<String> assignments;
	
	
	/**
	 * functionality to submit the assignment.
	 */
	public void submitAssignment() {
		
	}
	/**
	 * Gets list of courses for a Student.
	 * @return List of courses. 
	 */
	public List<String> getCourse() {
		return null;
	}
	/**
	 * Get list of assignments for this student.
	 * @return list of assignments. 
	 */
	public List<String> getAssignments(){
		return null;
	}
	
}
