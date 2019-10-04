package com.example.demo.regression;

public class ResultsStorer {
	
	public double plagiarismPercentage;
	public int lineCopied;
	
	public ResultsStorer(double plagiarismPercentage, int lineCopied) {
		this.plagiarismPercentage = plagiarismPercentage;
		this.lineCopied = lineCopied;
	}

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
<<<<<<< HEAD
}
=======
}
>>>>>>> AST and moss backend integrated
