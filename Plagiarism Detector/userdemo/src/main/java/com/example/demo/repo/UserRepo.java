package com.example.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
	User findByUserId(Long userId);
    User findByEmailId(String email);
    User findByEmailIdAndPassword(String email, String password);
}