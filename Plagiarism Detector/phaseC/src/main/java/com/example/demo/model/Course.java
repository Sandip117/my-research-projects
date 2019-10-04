package com.example.demo.model;

import java.util.List;

import javax.persistence.*;

/**
 *
 * class Course represents a course offered in a particular semester by a
 * particular professor.
 *
 */
@Entity
@Table(name = "NEW_COURSE")
public class Course {
	/**
	 * List of local variables or Columns in the entity "new_course" in the db
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "course_id")
	private Long courseId;
	@Column(name = "user_id", insertable = false, updatable = false)
	private Long userId;
	@Column(name = "semester_id", insertable = false, updatable = false)
	private Long semesterId;
	private String courseName;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "semester_id", insertable = true)
	private Semester semester;
	private String courseNumber;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id", insertable = true)
	private User faculty;
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	@JoinColumn(name = "course_id", insertable = false)
	private List<Assignment> assignments;
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	@JoinColumn(name = "course_id", insertable = false)
	private List<CourseRegistration> registrations;

	/**
	 * 
	 * Public getters and setters
	 */
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
		this.semester = semester;
	}

	public String getCourseNumber() {
		return courseNumber;
	}

	public void setCourseNumber(String courseNumber) {
		this.courseNumber = courseNumber;
	}

}
