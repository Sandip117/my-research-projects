package cs5500.plagiarism.service;

import java.util.Arrays;
import java.util.HashSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cs5500.plagiarism.model.Role;
import cs5500.plagiarism.model.User;
import cs5500.plagiarism.schema.RoleSchema;
import cs5500.plagiarism.schema.UserSchema;

/**
 * UserService class will let us query the database and
 * save items to the database. 
 * Below are some example snippets of this class implementation
 */
@Service("userService")
public class UserServiceImpl implements UserService{

	@Autowired
	private UserSchema userSchema;
	@Autowired
    private RoleSchema roles;
    
	@Override
	public User findUserByUsername(String username) {
		return userSchema.findByUsername(username);
	}

	@Override
	public void saveUser(User user) {
		Role userRole = roles.findByRole("ADMIN");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
		userSchema.saveUser(user);
	}

}
