package cs5500.plagiarism.service;
import cs5500.plagiarism.model.User;
/**
 * @since 02.10.2018
 * @author team211
 * required functionalities on User.
 */
public interface UserService {
	public User findUserByUsername(String username);
	public void saveUser(User user);
}