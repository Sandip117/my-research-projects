package com.example.demo.plagiarism;

public class Report {
	private String comparison;
	private String result;
	
	public Report(String comparison, String result) {
		this.comparison=comparison;
		this.result=result;
	}
	public String getComparisron() {
		return comparison;
	}
	public String getResult() {
		return result;
	}
}
