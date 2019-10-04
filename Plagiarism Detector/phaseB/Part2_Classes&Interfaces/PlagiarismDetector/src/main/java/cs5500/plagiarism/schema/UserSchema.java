package cs5500.plagiarism.schema;
import cs5500.plagiarism.model.User;
/**
 * @since 02.10.2018
 * @author team211
 * Operations that can be performed on User table. 
 */
public interface UserSchema {
	 User findByUsername(String username);
	 void saveUser(User user);
}
