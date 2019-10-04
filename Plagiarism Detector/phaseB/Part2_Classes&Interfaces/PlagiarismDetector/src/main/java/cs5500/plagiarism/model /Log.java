package cs5500.plagiarism.model;

import java.util.Date;
import java.util.List;
/**
 * Logs the list of users and their assignment.
 * @author team211
 * @since 02.10.2018
 */
public abstract class Log {
	String courseId;
	Date date; // Includes timestamp as well.
	Assignment a1;
	List<User> userList;
	
}
