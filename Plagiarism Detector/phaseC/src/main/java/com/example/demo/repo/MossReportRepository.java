package com.example.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.model.MossReport;

/**
 * This interface contains a list of db methods for the MossReport entity
 * 
 * @author sandipsamal
 *
 */
@Repository
public interface MossReportRepository extends JpaRepository<MossReport, Long> {
	/**
	 * Finds a report from its id
	 * 
	 * @param mossId
	 *            : a given moss id
	 * @return : a moss report from the db having id same as mossId
	 */
	MossReport getReportByMossId(Long mossId);

	/**
	 * Finds a list of moss reports for a given assignment id
	 * 
	 * @param assignmentId
	 *            : is a given assignment id
	 * @return : a list of moss reports for the assignment whose id is assignmmetId
	 */
	List<MossReport> getReportsByAssignmentId(Long assignmentId);

}
