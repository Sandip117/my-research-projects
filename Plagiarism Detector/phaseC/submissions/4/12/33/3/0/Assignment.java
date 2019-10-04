package cs5500.plagiarism.model;

import java.io.File;

/**
 * @since 02.10.2018 
 * @author team211
 * Assignment class: an Assignment file can be uploaded.
 */
public class Assignment {
	int assignmentId;
	String assignmentTitle;
	File f1;
	/**
	 * Getters and setters for class variable.
	 */
	public File getF1() {
		return f1;
	}
	public void setF1(File f1) {
		this.f1 = f1;
	}
	public int getAssignmentId() {
		return assignmentId;
	}
	public void setAssignmentId(int assignmentId) {
		this.assignmentId = assignmentId;
	}
	public String getAssignmentTitle() {
		return assignmentTitle;
	}
	public void setAssignmentTitle(String assignmentTitle) {
		this.assignmentTitle = assignmentTitle;
	}
	
}
