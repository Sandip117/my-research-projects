package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.MossReport;
import com.example.demo.repo.MossReportRepository;
@Service("MossReportService")
public class MossReportServiceImpl implements MossReportService {
	@Autowired
	MossReportRepository mossRepo;
<<<<<<< HEAD
=======
	
>>>>>>> plagiarism reports, results etc
	MossReportServiceImpl(){}
	public MossReportServiceImpl(MossReportRepository mossRepo) {
		this.mossRepo=mossRepo;
	}
	
	@Override
	public void addReport(MossReport report) {
		mossRepo.save(report);
		
	}

	@Override
	public void updateReport(MossReport report) {
		addReport(report);
		
	}

	@Override
	public void deleteReport(MossReport report) {
		mossRepo.delete(report);
		
	}

	@Override
	public MossReport viewReportByMossId(Long moId) {
		return mossRepo.getReportByMossId(moId);
	}

	@Override
	public List<MossReport> viewReportsByAssignmentId(Long asId) {
		return mossRepo.getReportsByAssignmentId(asId);
	}

}
