package com.example.demo.model;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 
 * @author sandipsamal This class represents a particular submission to a
 *         particular assignment made by a particular student ; including
 *         details like file name and link
 */

@Entity
@Table(name = "NEW_SUBMISSION")
public class Submission {

	/**
	 * List of local variables or columns present in the entity "new_submission"
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long submissionId;
	private String fileName;
	private String fileLink;
	@Column(name = "assignment_id", insertable = false, updatable = false)
	private Long assignmentId;
	@Column(name = "user_id", insertable = false, updatable = false)
	private Long userId;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "assignment_id", insertable = true)
	private Assignment assignment;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id", insertable = true)
	private User student;
	private Date submissionDate = Calendar.getInstance().getTime();

	/**
	 * 
	 * Public getters and setters
	 */
	public Long getSubmissionId() {
		return submissionId;
	}

	public void setSubmissionId(Long submissionId) {
		this.submissionId = submissionId;
	}

	public User getStudent() {
		return student;
	}

	public void setStudent(User student) {
		this.student = student;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileLink() {
		return fileLink;
	}

	public void setFileLink(String fileLink) {
		this.fileLink = fileLink;
	}

	public Assignment getAssignment() {
		return assignment;
	}

	public void setAssignment(Assignment assignment) {
		this.assignment = assignment;
	}

	public Date getSubmissionDate() {
		return submissionDate;
	}

	public void setSubmissionDate(Date submissionDate) {
		this.submissionDate = submissionDate;
	}

}
