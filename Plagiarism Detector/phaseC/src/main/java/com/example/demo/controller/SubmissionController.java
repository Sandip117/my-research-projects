package com.example.demo.controller;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.example.demo.model.Assignment;
import com.example.demo.model.Course;
import com.example.demo.model.MossReport;
import com.example.demo.model.Submission;
import com.example.demo.model.User;
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

/**
 * This class represents a controller for all submission related services
 * 
 * @author sandipsamal
 *
 */

@RestController
@RequestMapping("/api")
public class SubmissionController {
	Logger logger = Logger.getLogger(SubmissionController.class);
	/**
	 * a list of all services used are declared and autowired here
	 */
	@Autowired
	UserService userService; // Service which will do all data retrieval/manipulation work
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

	/**
	 * Uploads a file to the system
	 * 
	 * @param files
	 *            : a list of files
	 * @param cid
	 *            : then course id for which the file is submitted
	 * @param uid
	 *            : the user id of the student uploading the file
	 * @param aid
	 *            : the assignment id for which the file is submitted
	 * @param sid
	 *            : the semester id for which the file is submitted
	 * @param subid
	 *            : the submission id for which the file is submitted
	 * @return status ok iff the save operation is successful
	 * @throws URISyntaxException
	 */
	// -------------------------Upload
	// ---------------------------------------------------
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public ResponseEntity<?> uploadFiles(@RequestParam("myFiles") MultipartFile[] files,
			@RequestParam("course") String cid, @RequestParam("user") String uid, @RequestParam("assign") String aid,
			@RequestParam("sem") String sid, @RequestParam("subid") String subid) throws URISyntaxException {
		List<User> ulist = new ArrayList<>();
		Course foundCourse = courseService.findCourseById(Long.parseLong(cid));
		User faculty = foundCourse.getFaculty();
		MossReport report = new MossReport();
		report.setMossLink("http://moss.stanford.edu/results/666896482/match0.html");
		boolean uploaded = uploadService.uploadFiles(files, aid, uid, cid, sid, subid);
		if (uploaded || files.length != 0) {
			String link = "/#/student/" + uid;
			URI main = new URI(link);
			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.setLocation(main);
			Long assignID = (long) Integer.parseInt(aid);
			Long userID = (long) Integer.parseInt(uid);
			int subCount = 0;
			List<Submission> sList = subService.viewSubmissionsByAssignmentId(assignID);
			for (Submission s : sList) {
				if (s.getStudent().getId() != userID) {
					subCount++;
				}
			}
			if(subCount > 0) {
				//run plagiarism test after 5 mins
				new java.util.Timer().schedule(
						new java.util.TimerTask() {
							@Override
							public void run() {
								String dirPath = "/submissions/"+sid+"/"+cid+"/"+aid+"/";
								MossApi mossApi = new MossApi();
								try {
									Assignment asn = assignmentService.findAssignmentById(assignID);
									String language = asn.getlanguage();
									boolean isPython = language.equals("python") ? true: false; 
									List<PlagiarismStorer> psList = mossApi.getPlagiarism(dirPath, language);
									System.out.println(psList);
									FileOperationsAndOutput foao = new FileOperationsAndOutput();
									List<RegressionOutput> rList = foao.getRegressionOutput(sid, cid, aid);
									System.out.println(rList);
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
										ulist.add(userService.findByUserId(user1));
										ulist.add(userService.findByUserId(user2));
										mossReport.setUser2Id(user2);
										mossReport.setMossLink(ps.getLink());
										int file1Pc = Integer.parseInt(ps.getFile1Percent().substring(0, ps.getFile1Percent().length()-1));
										int file2Pc = Integer.parseInt(ps.getFile2Percent().substring(0, ps.getFile2Percent().length()-1));
										int avgPc = (file1Pc + file2Pc) / 2;
										mossReport.setPlagiarismScore(avgPc);
										mossReport.setUser1SubId(sub1);
										mossReport.setUser2SubId(sub2);
										mossReport.setLinesCopied(ps.getLinesCopied());

										if(isPython) {
											for(RegressionOutput ro: rList) {
												if(ro.getUser1Id()==user1 && ro.getUser2Id()==user2 || ro.getUser2Id()==user1 && ro.getUser1Id()==user2) {
													mossReport.setRegressionScore(ro.getRegressionScore());
												}
											}
										}
										else {
											mossReport.setRegressionScore(0.0);
										}
										System.out.println(mossReport.getRegressionScore());
										if(user1 != user2) {
											mossService.addReport(mossReport);
										}
									}
							List<String> emailList = new ArrayList<>();
							emailList.add(ulist.get(0).getEmailId());
							emailList.add(ulist.get(1).getEmailId());
							String asnName = asn.getAssignmentName();
							String courseName = asn.getCourse().getCourseName();
							MailGenerator m = new MailGenerator();
							m.sendMail(faculty.getEmailId(), emailList,
									"http://ec2-54-145-246-162.compute-1.amazonaws.com:8000/#/instructor/course/"
											+ cid, asnName, courseName);


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

	/**
	 * Fetches a report by a moss id
	 * 
	 * @param mossId
	 *            : a moss report id
	 * @return a moss report for mossId
	 */
	// ------------------------View Report by moss
	// id--------------------------------------
	@RequestMapping(value = "/report", method = RequestMethod.GET)
	public ResponseEntity<?> getReportByMossId(@RequestParam("mossId") Long mossId) {

		MossReport report = mossService.viewReportByMossId(mossId);
		return new ResponseEntity<>(report, HttpStatus.OK);
	}

	/**
	 * Fetches a list of moss reports for the given assignment id
	 * 
	 * @param assignId
	 *            : an assignment id
	 * @return : a list of moss reports for assignId
	 */

	// ------------------------View Reports by assignment
	// id--------------------------------------
	@RequestMapping(value = "/allReports", method = RequestMethod.GET)
	public ResponseEntity<?> getReportByAssignId(@RequestParam("assignId") Long assignId) {

		List<MossReport> reportList = mossService.viewReportsByAssignmentId(assignId);
		return new ResponseEntity<>(reportList, HttpStatus.OK);
	}

	/**
	 * Fetches a list of courses to a given user
	 * 
	 * @param userId
	 *            : an user id
	 * @return : a list of courses registered to userId
	 */
	// ------------------------ View all Registered Courses
	// -------------------------------
	@RequestMapping(value = "/studentRegisteredCourses", method = RequestMethod.GET)
	public ResponseEntity<?> getRegisteredCourses(@RequestBody Long userId) {

		List<Course> cList = courseRegService.viewCoursesByUserId(userId);
		return new ResponseEntity<>(cList, HttpStatus.OK);
	}

	/**
	 * Fetches all the assignments created for a particular course
	 * 
	 * @param courseId:
	 *            is a course id
	 * @return : a list of assignments for courseId
	 */

	// ------------------------ View all Assignments by Course
	// ----------------------------
	@RequestMapping(value = "/courseAssignments", method = RequestMethod.GET)
	public ResponseEntity<?> getAssignmentsByCourse(@RequestBody Long courseId) {

		List<Assignment> aList = assignmentService.findAssignmentByCourseId(courseId);
		return new ResponseEntity<>(aList, HttpStatus.OK);
	}

	/**
	 * Saves a submission to the system
	 * 
	 * @param submission
	 *            : is a Submission to be saved
	 * @return : status OK is save operation is successful
	 */
	// ------------------------ Submit Assignment
	// --------------------------------------------
	@RequestMapping(value = "/submission", method = RequestMethod.POST)

	public ResponseEntity<?> submitAssignment(@RequestBody Submission submission) {
		subService.addSubmission(submission);
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	/**
	 * Fetches all the submissions for an assignment id
	 * 
	 * @param aid
	 *            : an assignment id
	 * @return : a list of submissions for aid
	 */

	// -------------------------Get Submission By
	// Assignment------------------------------------
	@RequestMapping(value = "/submission", method = RequestMethod.GET)
	public ResponseEntity<?> getSubmissionsByAssignment(@RequestParam("assignId") long aid) {
		List<Submission> sList = subService.viewSubmissionsByAssignmentId(aid);
		return new ResponseEntity<>(sList, HttpStatus.OK);
	}

}
