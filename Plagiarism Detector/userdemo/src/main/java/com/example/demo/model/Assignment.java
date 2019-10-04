package com.example.demo.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 * class Assignment as a model 
 *
 */
@Entity
@Table(name = "NEW_ASSIGNMENT")
public class Assignment {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "assignment_id")
	private Long assignmentId;
	@Column(name ="course_id", insertable=false,updatable=false)
	private Long courseId;
	private String assignmentName;
	@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="course_id", insertable=true)
	@Cascade(CascadeType.DELETE)
    private Course course;
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
		this.course=course;
	}
	
}
