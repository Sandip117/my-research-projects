package com.example.demo.model;


import java.util.List;

import javax.persistence.*;

import org.eclipse.jdt.internal.compiler.ast.Assignment;
/**
 * Class Semester model
 *
 */
@Entity
@Table(name="NEW_SEMESTER")
public class Semester {
	
	// semester type example: fall 2016
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "semester_id")
	private Long semesterId;
	private String name;
	
	public void setSemesterId(Long semesterId) {
		this.semesterId=semesterId;
	}
	public Long getSemesterId() {
		return semesterId;
	}
	public void setName(String name) {
		this.name=name;
	}
	public String getName() {
		return name;
	}
	
}
