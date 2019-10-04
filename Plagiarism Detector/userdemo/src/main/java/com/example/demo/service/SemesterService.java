package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Course;
import com.example.demo.model.Semester;

public interface SemesterService {
	void addSemester (Semester semester);
	void deleteSemester(Semester semester);
	void updateSemester(Semester semester);
	void deleteAllSemester();
	Semester findSemesterById(Long semesterId);
	List<Semester> viewAllSemesters();
	
}
