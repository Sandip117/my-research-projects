package cs5500.plagiarism.schema;

import java.util.List;
import cs5500.plagiarism.model.Assignment;

public interface AssignmentSchema {
	List<Assignment> findAssignmentsByCourse();
}
