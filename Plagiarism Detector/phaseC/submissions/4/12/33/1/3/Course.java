package cs5500.plagiarism.model;
/**
 * @since 02.10.2018 
 * @author team211
 * Hold the courses for a User.
 */
public class Course {
	String courseId;
	String courseName;
	/**
	 * Getters and setters for course.
	 * 
	 */
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	
	
}
