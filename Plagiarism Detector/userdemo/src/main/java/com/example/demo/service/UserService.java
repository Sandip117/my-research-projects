package com.example.demo.service;

import java.util.List;

import com.example.demo.model.User;

public interface UserService {

	String findRoleByEmailId(String username);

	User findByUsername(String username);

	User findByUserId(Long userId);

	void saveUser(User user);

	void updateUser(User user);

	void deleteUserById(Long id);

	void deleteAllUsers();

	List<User> findAllUsers();

	boolean isUserExist(User user);
	User findByUsernameAndPassword(String username, String password);
}

