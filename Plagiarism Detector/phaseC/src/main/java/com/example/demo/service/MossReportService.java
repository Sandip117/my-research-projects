package com.example.demo.service;

import java.util.List;

import com.example.demo.model.MossReport;

/**
 * This interface contains a list of services provided for MossReport
 * 
 * @author sandipsamal
 *
 */
public interface MossReportService {
	/**
	 * Adds a moss report in the system
	 * 
	 * @param report
	 *            : a given moss report
	 */
	void addReport(MossReport report);

	/**
	 * updates an existing moss report
	 * 
	 * @param report
	 *            : a given moss report
	 */
	void updateReport(MossReport report);

	/**
	 * deletes an moss report
	 * 
	 * @param report
	 *            : a given moss report
	 */
	void deleteReport(MossReport report);

	/**
	 * Finds a moss report by its id
	 * 
	 * @param moId
	 *            : a given moss id
	 * @return : a moss report whose id is moId
	 */
	MossReport viewReportByMossId(Long moId);

	/**
	 * Finds a list of moss reports for a given assignment id
	 * 
	 * @param asId
	 *            : a given assignment id
	 * @return : a list of moss reports for the assignment with id as asId
	 */
	List<MossReport> viewReportsByAssignmentId(Long asId);
}
