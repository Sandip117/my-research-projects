package com.example.demo.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import com.example.demo.model.User;
import com.example.demo.repo.UserRepo;

@Service("UserService")
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepo userRepo;
	UserServiceImpl (){};
	public UserServiceImpl(UserRepo userRepo) {
		this.userRepo=userRepo;
	}
	@Override
	public String findRoleByEmailId(String username) {
		User foundUser=findByUsername(username);
		return foundUser.getRole();
	}
	@Override
	public User findByUsername(String username) {
		return userRepo.findByEmailId(username);
	}
	@Override
	public User findByUserId(Long userId) {
		return userRepo.findByUserId(userId);
	}
	@Override
	public void saveUser(User user) {
		userRepo.save(user);
		
	}
	@Override
	public void updateUser(User user) {
		saveUser(user);
		
	}
	@Override
	public void deleteUserById(Long id) {
		User foundUser=findByUserId(id);
		userRepo.delete(foundUser);
		
	}
	@Override
	public void deleteAllUsers() {
		userRepo.deleteAll();
		
	}
	@Override
	public List<User> findAllUsers() {
		return userRepo.findAll();
	}
	@Override
	public boolean isUserExist(User user) {
		return userRepo.findByEmailId(user.getEmailId())!=null;
	}
	@Override
	public User findByUsernameAndPassword(String username, String password) {
		return userRepo.findByEmailIdAndPassword(username, password);
	}
	

}
