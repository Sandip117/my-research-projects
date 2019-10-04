package com.example.demo.repo;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.model.MossReport;
@Repository
public interface MossReportRepository extends JpaRepository<MossReport,Long> {
	MossReport getReportByMossId(Long mossId);
	List<MossReport> getReportsByAssignmentId(Long assignmentId);

}
