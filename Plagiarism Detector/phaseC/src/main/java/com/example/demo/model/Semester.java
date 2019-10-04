package com.example.demo.model;

import java.util.List;

import javax.persistence.*;

/**
 * Class Semester represents a semester in a year
 *
 */
@Entity
@Table(name = "NEW_SEMESTER")
public class Semester {

	/**
	 * List of local variables or columns present in the table "new_semester"
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "semester_id")
	private Long semesterId;
	private String name;
	private int isCurrent;
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	@JoinColumn(name = "semester_id", insertable = false)
	private List<Course> courses;

	/**
	 * 
	 * public getters and setters
	 */
	public void setSemesterId(Long semesterId) {
		this.semesterId = semesterId;
	}

	public Long getSemesterId() {
		return semesterId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public int getIsCurrent() {
		return isCurrent;
	}

	public void setIsCurrent(int isCurrent) {
		this.isCurrent = isCurrent;
	}

}
