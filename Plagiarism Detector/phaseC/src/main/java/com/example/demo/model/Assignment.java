package com.example.demo.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * class Assignment as a model The entity name in DB is "new_assignment" The
 * class represents a particular assignment assigned to a course
 *
 */
@Entity
@Table(name = "NEW_ASSIGNMENT")
public class Assignment {
	/**
	 * List of local variables Also, the list of columns in the entity
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "assignment_id")
	private Long assignmentId;
	@Column(name = "course_id", insertable = false, updatable = false)
	private Long courseId;
	private String assignmentName;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "course_id", insertable = true)
	private Course course;
	private String assignmentDetails;
	private Date dueDate;
	private String language;
	@OneToMany(fetch=FetchType.EAGER,cascade=CascadeType.REMOVE)
	@JoinColumn(name="assignment_id", insertable=false)
	private List<Submission> submissions;
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	@JoinColumn(name = "assignment_id", insertable = false)
	private List<MossReport> reports;

	/**
	 * Public getters and setters
	 */
	public Long getAssignmentId() {
		return assignmentId;
	}

	public void setAssignmentId(Long assignmentId) {
		this.assignmentId = assignmentId;
	}

	public String getAssignmentName() {
		return assignmentName;
	}

	public void setAssignmentName(String assignmentName) {
		this.assignmentName = assignmentName;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public String getAssignmentDetails() {
		return assignmentDetails;
	}

	public void setAssignmentDetails(String assignmentDetails) {
		this.assignmentDetails = assignmentDetails;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	public String getlanguage() {
		return language;
	}
	public void setlanguage(String language) {
		this.language = language;
	}

}
