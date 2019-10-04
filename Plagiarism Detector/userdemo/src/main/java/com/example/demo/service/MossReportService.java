package com.example.demo.service;

import java.util.List;

import com.example.demo.model.MossReport;

public interface MossReportService {
	void addReport(MossReport report);
	void updateReport(MossReport report);
	void deleteReport(MossReport report);
	MossReport viewReportByMossId(Long moId);
	List<MossReport> viewReportsByAssignmentId(Long asId);
}
