package com.example.demo.model;

import javax.persistence.*;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;



/**
 *
 * class Course
 *
 */
@Entity
@Table(name = "NEW_COURSE")
public class Course {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "course_id")
	private Long courseId;
	@Column(name="user_id",insertable=false,updatable=false)
	private Long userId;
	@Column(name="semester_id",insertable=false,updatable=false)
	private Long semesterId;
	private String courseName;
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="semester_id", insertable=true)
	private Semester semester;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="user_id", insertable=true)
	private User faculty;
	
	public Long getCourseId() {
		return courseId;
	}
	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}
	public User getFaculty() {
		return faculty;
	}
	public void setFaculty(User faculty) {
		this.faculty = faculty;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	
	public Semester getSemester() {
		return semester;
	}
	public void setSemester(Semester semester) {
		this.semester=semester;
	}
	
}
