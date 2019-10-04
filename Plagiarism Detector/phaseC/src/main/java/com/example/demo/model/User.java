package com.example.demo.model;

import java.util.List;

import javax.persistence.*;

/**
 * 
 * @author sandipsamal This class represents a user to this system.
 */
@Entity
@Table(name = "NEW_USER")
public class User {
	/**
	 * List of local variables or columns present in the entity "new-user"
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id")
	private Long userId;
	private String emailId;
	private String password;
	private String role;
	private String firstName;
	private String lastName;
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	@JoinColumn(name = "user_id", insertable = false)
	private List<Course> courses;
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	@JoinColumn(name = "user_id", insertable = false)
	private List<CourseRegistration> registrations;
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	@JoinColumn(name = "user_id", insertable = false)
	private List<Submission> submissions;

	/**
	 * 
	 * public getters and setters
	 */
	public Long getId() {
		return userId;
	}

	public void setId(Long userId) {
		this.userId = userId;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

}
