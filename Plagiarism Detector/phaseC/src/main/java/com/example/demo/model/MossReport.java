package com.example.demo.model;

import javax.persistence.*;

/**
 * 
 * @author sandipsamal This class represents a single report of plagiarism. This
 *         includes the students involved, the submissions and assignment
 */
@Entity
@Table(name = "NEW_MOSS_REPORT")
public class MossReport {
	/**
	 * List of local variables or columns present in the entity "new_moss_report"
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "moss_id")
	private Long mossId;
	@Column(name = "assignment_id", insertable = false, updatable = false)
	private Long assignmentId;
	@Column(name = "user1_id")
	private Long user1Id;
	@Column(name = "user2_id")
	private Long user2Id;
	@Column(name = "user1_sub_id")
	private Long user1SubId;
	@Column(name = "user2_sub_id")
	private Long user2SubId;
	private double plagiarismScore;
	private String mossLink;
	@Column(name = "regression_score")
	private double regressionScore;

	@Column(name = "lines_copied")
	private int linesCopied;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "assignment_id", insertable = true)
	private Assignment assignment;

	/**
	 * 
	 * Public getters and setters
	 */
	public Long getMossId() {
		return mossId;
	}

	public double getRegressionScore() {
		return regressionScore;
	}

	public void setRegressionScore(double regressionScore) {
		this.regressionScore = regressionScore;
	}

	public int getLinesCopied() {
		return linesCopied;
	}

	public void setLinesCopied(int linesCopied) {
		this.linesCopied = linesCopied;
	}

	public void setMossId(Long mossId) {
		this.mossId = mossId;
	}

	public Long getUser1Id() {
		return user1Id;
	}

	public void setUser1Id(Long user1Id) {
		this.user1Id = user1Id;
	}

	public Long getUser2Id() {
		return user2Id;
	}

	public void setUser2Id(Long user2Id) {
		this.user2Id = user2Id;
	}

	public Long getUser1SubId() {
		return user1SubId;
	}

	public void setUser1SubId(Long user1SubId) {
		this.user1SubId = user1SubId;
	}

	public Long getUser2SubId() {
		return user2SubId;
	}

	public void setUser2SubId(Long user2SubId) {
		this.user2SubId = user2SubId;
	}

	public double getPlagiarismScore() {
		return plagiarismScore;
	}

	public void setPlagiarismScore(double plagiarismScore) {
		this.plagiarismScore = plagiarismScore;
	}

	public String getMossLink() {
		return mossLink;
	}

	public void setMossLink(String mossLink) {
		this.mossLink = mossLink;
	}

	public Assignment getAssignment() {
		return assignment;
	}

	public void setAssignment(Assignment assignment) {
		this.assignment = assignment;
	}

}
