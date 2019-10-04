package cs5500.plagiarism.service;

import java.util.List;
import cs5500.plagiarism.model.Assignment;
import cs5500.plagiarism.model.Course;

public interface AssignmentService {
	public List<Assignment> findAssignmentByCourse(Course course);
	public void saveAssignment(Assignment assign);
}
