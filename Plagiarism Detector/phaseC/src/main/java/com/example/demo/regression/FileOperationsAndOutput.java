package com.example.demo.regression;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.features.*;
import com.example.demo.lineSimilarity.SimilarityLineMetric;

/**
 * Output of the datasets.
 * @author team211
 *
 */
public class FileOperationsAndOutput {
	double astOutput[];
	double mossOutput[]; 
	ClassLoader classLoader = getClass().getClassLoader();


	public FileOperationsAndOutput(double astOutput[], double mossOutput[]) {
		this.astOutput = astOutput;
		this.mossOutput = mossOutput;
	}

	public FileOperationsAndOutput() {

	}

	public String createPath(String semesterId, String courseId, String assignmentId, String studentId, String submissionId) {
		return semesterId + "/" + courseId + "/" + assignmentId + "/" + studentId + "/" + submissionId; 
	}
	
	public List<RegressionOutput> getRegressionOutput(String semId, String courseId, String assignId) throws IOException{
	
		FileOperationsAndOutput loader = new FileOperationsAndOutput();
		FileOperationsAndOutput output = LinearRegression.loadDataSet();
		FeatureCaller feat = new FeatureCaller();
		List<ResultsStorer> resultlist = new ArrayList<ResultsStorer>();
		
		String rootPath = new File("submissions") +"/"+semId+"/"+courseId+"/"+assignId;
		File[] directories = new File(rootPath).listFiles(File::isDirectory);
		File[] first = null;
		File[] second = null;
		int numberOfStudents = directories.length;
		List<Integer> userList = new ArrayList<>();
		List<RegressionOutput> outputlist = new ArrayList<>();
		RegressionOutput solution;
		for (File file : directories) {
			userList.add(Integer.parseInt(file.getName()));
		}
		LinearRegression lr = new LinearRegression(output.astOutput, output.mossOutput);
		SimilarityLineMetric sm = new SimilarityLineMetric();
		for(int i = 0; i < numberOfStudents; i++) {
			for(int j =i+1; j < numberOfStudents; j++) {
				first = directories[i].listFiles();
				for (File dir1 : first) {
					File [] listOfFiles1 = dir1.listFiles();
					second = directories[j].listFiles();
					for (File dir2 : second) {
						File [] listOfFiles2 = dir2.listFiles();
						for (File file1 : listOfFiles1) {
							for(File file2: listOfFiles2) {
								double finalScoreAfterRegression = lr.predict(feat.finalPlagiarismScore(file1, file2));
								resultlist.add(new ResultsStorer(finalScoreAfterRegression, sm.similar(file1, file2)));
							}
						}					

					}
				}
				int sum = 0;
				for (ResultsStorer results : resultlist) {
					sum += results.plagiarismPercentage;
				}
				solution = new RegressionOutput(userList.get(i), userList.get(j), sum/(resultlist.size()));
				outputlist.add(solution);
			}
		}
		return outputlist;
			
	}
		
}