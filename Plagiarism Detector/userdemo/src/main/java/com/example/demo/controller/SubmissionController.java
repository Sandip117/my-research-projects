package com.example.demo.controller;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.demo.model.Assignment;
import com.example.demo.model.Course;
import com.example.demo.model.MossReport;
import com.example.demo.model.Submission;
import com.example.demo.moss.MossApi;
import com.example.demo.moss.PlagiarismStorer;
import com.example.demo.regression.FileOperationsAndOutput;
import com.example.demo.regression.RegressionOutput;
import com.example.demo.service.AssignmentService;
import com.example.demo.service.CourseRegistrationService;
import com.example.demo.service.CourseService;
import com.example.demo.service.MailGenerator;
import com.example.demo.service.MossReportService;
import com.example.demo.service.SubmissionService;
import com.example.demo.service.UploadService;
import com.example.demo.service.UserService;

import it.zielke.moji.MossException;



@RestController
@RequestMapping("/api")
public class SubmissionController {

	Logger logger = Logger.getLogger(SubmissionController.class);

	@Autowired
	UserService userService; //Service which will do all data retrieval/manipulation work
	@Autowired
	CourseService courseService;
	@Autowired
	AssignmentService assignmentService;
	@Autowired
	CourseRegistrationService courseRegService;
	@Autowired
	SubmissionService subService;
	@Autowired
	UploadService uploadService;
	@Autowired
	MossReportService mossService;

	//-------------------------Upload ---------------------------------------------------
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public ResponseEntity<?> uploadFiles(@RequestParam("myFiles") MultipartFile[] files, @RequestParam("course") String cid, @RequestParam("user") String uid, @RequestParam("assign") String aid, @RequestParam("sem") String sid, @RequestParam("subid") String subid) throws URISyntaxException {
			boolean uploaded = uploadService.uploadFiles(files, aid, uid, cid, sid, subid);
			if (uploaded || files.length != 0){
				String link = "/#/student/"+uid;
				URI main = new URI(link);
				HttpHeaders httpHeaders = new HttpHeaders();
				httpHeaders.setLocation(main);
				Long assignID = (long) Integer.parseInt(aid);
				Long userID = (long) Integer.parseInt(uid);
				int subCount = 0;
				List<Submission> sList = subService.viewSubmissionsByAssignmentId(assignID);
				for (Submission s: sList) {
					if(s.getStudent().getId()!= userID) {
						subCount++;
					}
				}

				if(subCount > 0) {
					//run plagiarism test after 5 mins
					new java.util.Timer().schedule(
							new java.util.TimerTask() {
								@Override
								public void run() {
									String dirPath = "submissions/"+sid+"/"+cid+"/"+aid+"/";
									MossApi mossApi = new MossApi();
									try {
										List<PlagiarismStorer> psList = mossApi.getPlagiarism(dirPath);
										System.out.println(psList);
										
										FileOperationsAndOutput foao = new FileOperationsAndOutput();
										List<RegressionOutput> rList = foao.getRegressionOutput(sid, cid, aid);
										System.out.println(rList);
										

										Assignment asn = assignmentService.findAssignmentById(assignID);
										

										for(PlagiarismStorer ps : psList) {
											
											
											MossReport mossReport = new MossReport();
											mossReport.setAssignment(asn);
											String file1 = ps.getFile1();
											String[] file1Folders = file1.split("/");
											Long user1 = (long) Integer.parseInt(file1Folders[file1Folders.length-2]);
											Long sub1 = (long) Integer.parseInt(file1Folders[file1Folders.length-1]);
											mossReport.setUser1Id(user1);
											String file2 = ps.getFile2();
											String[] file2Folders = file2.split("/");
											Long user2 = (long) Integer.parseInt(file2Folders[file2Folders.length-2]);
											Long sub2 = (long) Integer.parseInt(file2Folders[file2Folders.length-1]);

											mossReport.setUser2Id(user2);
											mossReport.setMossLink(ps.getLink());
											int file1Pc = Integer.parseInt(ps.getFile1Percent().substring(0, ps.getFile1Percent().length()-1));
											int file2Pc = Integer.parseInt(ps.getFile2Percent().substring(0, ps.getFile2Percent().length()-1));
											int avgPc = (file1Pc + file2Pc) / 2;
											mossReport.setPlagiarismScore(avgPc);
											mossReport.setUser1SubId(sub1);
											mossReport.setUser2SubId(sub2);
											mossReport.setLinesCopied(ps.getLinesCopied());
											for(RegressionOutput ro: rList) {
												if(ro.getUser1Id()==user1 && ro.getUser2Id()==user2 || ro.getUser2Id()==user1 && ro.getUser1Id()==user2) {
													mossReport.setRegressionScore(ro.getRegressionScore());
												}
											}
											System.out.println(mossReport.getRegressionScore());
											if(user1 != user2) {
												mossService.addReport(mossReport);
											}
										}
										
										
										
										MailGenerator m = new MailGenerator();
										m.sendMail("iamharshmehta@gmail.com");

									} catch (MossException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}

									// your code here
								}
							},
							5000
//				        	30000
							);
				}

				return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
			}
	}

	//------------------------View Report by moss id--------------------------------------
	@RequestMapping(value = "/report", method = RequestMethod.GET)
	public ResponseEntity<?> getReportByMossId(@RequestParam("mossId") Long mossId) {

		MossReport report = mossService.viewReportByMossId(mossId);
		return new ResponseEntity<>(report,HttpStatus.OK);
	}


	//------------------------View Reports by assignment id--------------------------------------
	@RequestMapping(value = "/allReports", method = RequestMethod.GET)
	public ResponseEntity<?> getReportByAssignId(@RequestParam("assignId") Long assignId) {

		List<MossReport> reportList = mossService.viewReportsByAssignmentId(assignId);
		return new ResponseEntity<>(reportList,HttpStatus.OK);
	}
	//------------------------ View all Registered Courses -------------------------------
	@RequestMapping(value = "/studentRegisteredCourses", method = RequestMethod.GET)
	public ResponseEntity<?> getRegisteredCourses(@RequestBody Long userId) {

		List<Course> cList = courseRegService.viewCoursesByUserId(userId);
		return new ResponseEntity<>(cList,HttpStatus.OK);
	}

	//------------------------ View all Assignments by Course ----------------------------
	@RequestMapping(value = "/courseAssignments", method = RequestMethod.GET)
	public ResponseEntity<?> getAssignmentsByCourse(@RequestBody Long courseId) {

		List<Assignment> aList = assignmentService.findAssignmentByCourseId(courseId);
		return new ResponseEntity<>(aList,HttpStatus.OK);
	}
	//------------------------ Submit Assignment --------------------------------------------
	@RequestMapping(value = "/submission", method = RequestMethod.POST)

	public ResponseEntity<?> submitAssignment(@RequestBody Submission submission) {
		subService.addSubmission(submission);
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	//-------------------------Get Submission By Assignment------------------------------------
	@RequestMapping(value="/submission", method = RequestMethod.GET)
	public ResponseEntity<?> getSubmissionsByAssignment(@RequestParam("assignId") long aid) {
		List<Submission> sList = subService.viewSubmissionsByAssignmentId(aid);
		return new ResponseEntity<>(sList,HttpStatus.OK);
	}


}
