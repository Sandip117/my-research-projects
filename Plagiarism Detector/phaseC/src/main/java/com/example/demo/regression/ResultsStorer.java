package com.example.demo.regression;
/**
 * A class to store the plagiarism results
 * @author sandipsamal
 *
 */
public class ResultsStorer {
	
	public double plagiarismPercentage;
	public int lineCopied;
	
	public ResultsStorer(double plagiarismPercentage, int lineCopied) {
		this.plagiarismPercentage = plagiarismPercentage;
		this.lineCopied = lineCopied;
	}

	/**
	 * public getters and setters
	 */
	public double getPlagiarismPercentage() {
		return plagiarismPercentage;
	}

	public void setPlagiarismPercentage(double plagiarismPercentage) {
		this.plagiarismPercentage = plagiarismPercentage;
	}

	public int getLineCopied() {
		return lineCopied;
	}

	public void setLineCopied(int lineCopied) {
		this.lineCopied = lineCopied;
	}
}
