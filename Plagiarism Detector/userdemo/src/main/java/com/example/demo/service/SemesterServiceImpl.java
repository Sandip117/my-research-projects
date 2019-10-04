package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Course;
import com.example.demo.model.Semester;
import com.example.demo.repo.SemesterRepository;
@Service("SemesterService")
public class SemesterServiceImpl implements SemesterService {
	SemesterServiceImpl(){}
	public SemesterServiceImpl(SemesterRepository semRepo){
		this.semRepo=semRepo;
	}
	@Autowired
	SemesterRepository semRepo;
	@Override
	public void addSemester(Semester semester) {
		semRepo.save(semester);
		
	}

	@Override
	public void deleteSemester(Semester semester) {
		semRepo.delete(semester);
		
	}

	@Override
	public void updateSemester(Semester semester) {
		addSemester(semester);
		
	}

	@Override
	public List<Semester> viewAllSemesters() {
		
		return semRepo.findAll();
	}

	@Override
	public void deleteAllSemester() {
		semRepo.deleteAll();
		
	}
	@Override
	public Semester findSemesterById(Long semesterId) {
		return semRepo.findBySemesterId(semesterId);
	}

}
