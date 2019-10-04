package com.example.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Semester;
@Repository
public interface SemesterRepository extends JpaRepository<Semester, Long> {
	Semester findBySemesterId(Long semesterId);
}
