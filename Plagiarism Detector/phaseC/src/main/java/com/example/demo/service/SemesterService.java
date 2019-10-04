package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Course;
import com.example.demo.model.Semester;

/**
 * This interface contains a list of services provided for the Semester entity
 * 
 * @author sandipsamal
 *
 */
public interface SemesterService {
	/**
	 * Adds a semester to the system
	 * 
	 * @param semester
	 *            : a given semester
	 */
	void addSemester(Semester semester);

	/**
	 * removes a semester from the system
	 * 
	 * @param semester
	 *            : a given semester
	 */
	void deleteSemester(Semester semester);

	/**
	 * updates an existing semester
	 * 
	 * @param semester
	 *            : a given semester
	 */
	void updateSemester(Semester semester);

	/**
	 * Resets the current semester in the system
	 */
	void resetCurrentSemester();

	/**
	 * sets a given semester as current semester in the system
	 * 
	 * @param semester
	 *            : a given semester
	 */
	void setCurrentSemester(Semester semester);

	/**
	 * removes all semester from the system
	 */
	void deleteAllSemester();

	/**
	 * finds a semester by id
	 * 
	 * @param semesterId
	 *            : a given semester id
	 * @return : a found semester for semesterId
	 */
	Semester findSemesterById(Long semesterId);

	/**
	 * finds a semester by its isCurrent value
	 * 
	 * @param isCurrent
	 *            : an variable whose value can be 0 or 1
	 * @return : the current semester of the system
	 */
	Semester findSemesterByIsCurrent(int isCurrent);

	/**
	 * Finds the list of all semester in the system
	 * 
	 * @return : a list of all semesters
	 */
	List<Semester> viewAllSemesters();

}
