package com.example.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Semester;

/**
 * This interface contains a list of db methods for the Semester entity
 * 
 * @author sandipsamal
 *
 */
@Repository
public interface SemesterRepository extends JpaRepository<Semester, Long> {
	/**
	 * Finds a semester from its id
	 * 
	 * @param semesterId
	 *            : a semester id
	 * @return : a semester from the db whose id is semesterId
	 */
	Semester findBySemesterId(Long semesterId);

	/**
	 * Finds the current semester from the system
	 * 
	 * @param isCurrent
	 *            : is an integer variable whose value can be either 0 or 1
	 * @return : returns a semester which is the current semester
	 */
	Semester findByIsCurrent(int isCurrent);
}
