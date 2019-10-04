package com.example.demo.regression;
/**
 * This class represents a regression output for the similarity
 * for the files of two different users
 * @author sandipsamal
 *
 */
public class RegressionOutput {
	int user1Id;
	int user2Id;
	double regressionScore;
	
	
	public RegressionOutput() {
		super();
	}
	public RegressionOutput(int user1Id, int user2Id, double regressionScore) {
		super();
		this.user1Id = user1Id;
		this.user2Id = user2Id;
		this.regressionScore = regressionScore;
	}
	/**
	 * public getters and setters
	 */
	public int getUser1Id() {
		return user1Id;
	}
	public void setUser1Id(int user1Id) {
		this.user1Id = user1Id;
	}
	public int getUser2Id() {
		return user2Id;
	}
	public void setUser2Id(int user2Id) {
		this.user2Id = user2Id;
	}
	public double getRegressionScore() {
		return regressionScore;
	}
	public void setRegressionScore(double regressionScore) {
		this.regressionScore = regressionScore;
	}
	
}