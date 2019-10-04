package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.model.Semester;
import com.example.demo.repo.SemesterRepository;

/**
 * This class implements the SemesterService
 * 
 * @author sandipsamal
 *
 */
@Service("SemesterService")
public class SemesterServiceImpl implements SemesterService {
	/**
	 * Java Constructor and repository autowired here
	 */
	SemesterServiceImpl() {
	}

	public SemesterServiceImpl(SemesterRepository semRepo) {
		this.semRepo = semRepo;
	}

	@Autowired
	SemesterRepository semRepo;

	/**
	 * public methods overridden below
	 */
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

	@Override
	public void resetCurrentSemester() {
		Semester foundSem = findSemesterByIsCurrent(1);
		if (foundSem != null) {
			foundSem.setIsCurrent(0);
			updateSemester(foundSem);
		}

	}

	@Override
	public void setCurrentSemester(Semester semester) {
		resetCurrentSemester();
		semester.setIsCurrent(1);
		updateSemester(semester);

	}

	@Override
	public Semester findSemesterByIsCurrent(int isCurrent) {
		return semRepo.findByIsCurrent(isCurrent);
	}

}
