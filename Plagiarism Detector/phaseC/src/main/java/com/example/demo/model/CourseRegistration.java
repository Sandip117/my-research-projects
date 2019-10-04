package com.example.demo.model;

import javax.persistence.*;

/**
 * 
 * @author sandipsamal This class represents a particular record of course
 *         registered to a particular student.
 */
@Entity
@Table(name = "NEW_COURSE_REGISTRATION")
public class CourseRegistration {
	/**
	 * List of local variables/ columns in the entity "new_course_registration"
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "registration_id")
	private Long registrationId;
	@Column(name = "user_id", insertable = false, updatable = false)
	private Long userId;
	@Column(name = "course_id", insertable = false, updatable = false)
	private Long courseId;
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id", insertable = true)
	private User student;
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "course_id", insertable = true)
	private Course course;

	/**
	 * 
	 * Public getters and setters
	 */
	public Long getRegistrationId() {
		return registrationId;
	}

	public void setRegistrationId(Long registrationId) {
		this.registrationId = registrationId;
	}

	public User getStudent() {
		return student;
	}

	public void setStudent(User student) {
		this.student = student;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

}
