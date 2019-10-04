package com.example.demo.regression;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.features.*;
<<<<<<< HEAD
=======
import com.example.demo.lineSimilarity.SimilarityLineMetric;
>>>>>>> AST and moss backend integrated
import com.example.demo.moss.MossApi;

import it.zielke.moji.MossException;

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
<<<<<<< HEAD
 	}
=======
	}
<<<<<<< HEAD
>>>>>>> AST and moss backend integrated

	public static void main(String...args) throws IOException, URISyntaxException, MossException {
=======
	
	public List<RegressionOutput> getRegressionOutput(String semId, String courseId, String assignId) throws IOException{
	
>>>>>>> regression done
		FileOperationsAndOutput loader = new FileOperationsAndOutput();
		FileOperationsAndOutput output = LinearRegression.loadDataSet();
		FeatureCaller feat = new FeatureCaller();
		List<ResultsStorer> resultlist = new ArrayList<ResultsStorer>();
<<<<<<< HEAD
<<<<<<< HEAD
		String path1 = "sem1/courseid1/assignmentid/studentid1/submissionid1";
		String path2 = "sem1/courseid1/assignmentid/studentid2/submissionid2";
=======
		String path1 = "sem1/courseid1/assignmentId/studentid1/submissionid1";
		String path2 = "sem1/courseid1/assignmentId/studentid2/submissionid2";
>>>>>>> AST and moss backend integrated
		URL url = loader.classLoader.getResource(path1);
		URL url2 = loader.classLoader.getResource(path2);
=======
		//String path1 = "sem1/courseid1/assignmentId/studentid1/submissionid1";
		//		String rootPath = "sem1/courseid1/assignmentId/studentid2/submissionid2";

		String rootPath = new File("submissions").getAbsolutePath()+"/"+semId+"/"+courseId+"/"+assignId;
		//		String rootPath = "/Users/surajsatheeshnair/Documents/NEU/MSD/ngBranchRepo/team-211/userdemo/submissions/4/1/32";
		//URL url = loader.classLoader.getResource(path2);
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
>>>>>>> regression done
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
						//						

					}
				}
				int sum = 0;
				for (ResultsStorer results : resultlist) {
					System.out.println(results.plagiarismPercentage);
					sum += results.plagiarismPercentage;
				}
				solution = new RegressionOutput(userList.get(i), userList.get(j), sum/(resultlist.size()));
				outputlist.add(solution);
			}
		}
		System.out.println(outputlist);
		return outputlist;
			
	}
		// System.out.println(resultlist);
		
<<<<<<< HEAD
		// Moss output
		MossApi moss = new MossApi();
<<<<<<< HEAD
		String mossPath =  "../userdemo/submissions/sem1/courseid1/assignmentid";
=======
		String mossPath =  "../userdemo/submissions/sem1/courseid1/assignmentId";
>>>>>>> AST and moss backend integrated
		URL mossurl  = moss.getMossURL(mossPath);
		System.out.println(mossurl.toString());
	}
=======
>>>>>>> regression done
}