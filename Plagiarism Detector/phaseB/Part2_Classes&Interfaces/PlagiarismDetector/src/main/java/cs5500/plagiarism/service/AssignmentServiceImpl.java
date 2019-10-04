package cs5500.plagiarism.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cs5500.plagiarism.model.Assignment;
import cs5500.plagiarism.model.Course;
/**
 * @since 02.10.2018
 * @author team211
 * All assignment related functionalities will be in this class.
 */
@Service("assignmentService")
public class AssignmentServiceImpl implements AssignmentService {

	@Override
	public List<Assignment> findAssignmentByCourse(Course course) {
		return null;
	}

	@Override
	public void saveAssignment(Assignment assign) {
		
	}

}
