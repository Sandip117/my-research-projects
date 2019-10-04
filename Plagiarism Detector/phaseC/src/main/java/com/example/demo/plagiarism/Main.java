package com.example.demo.plagiarism;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.FileUtils;


public class Main {
	public static void main(String [] args) throws IOException {
		getComparison("course","homework");
	}

	public static String getComparison(String course, String hw) throws IOException {
		downloadJar("https://github.com/jplag/jplag/releases/download/v2.11.9-SNAPSHOT/jplag-2.11.9-SNAPSHOT-jar-with-dependencies.jar");
		String command="java -jar jplag-2.11.9-SNAPSHOT-jar-with-dependencies.jar -l java17 -r plagiarismResults  ";
		command = command +"src/main/java/com/example/demo/plagiarism/Reports/"+ course+"/"+hw;
		
		//StringBuilder output = new StringBuilder();
		List<String> output=new ArrayList<String>();
		Process p;
		try {
			p = Runtime.getRuntime().exec(command);
			p.waitFor();
			BufferedReader reader =
                            new BufferedReader(new InputStreamReader(p.getInputStream()));

                        String line = "";
			while ((line = reader.readLine())!= null) {
				output.add(line);
			}

		} catch (Exception e) {
			
		}
		List<Report> lr=new ArrayList<Report>();
		for(int i=9;i<output.size()-3;i++) {
			String [] arr=output.get(i).substring(9).split(":");
			Report r = new Report(arr[0],arr[1]);
			lr.add(r);
		}
		int j=0;
		for(Report r:lr)
			System.out.println("Comparison :" + r.getComparisron() + "  Result :" + r.getResult());
		
		return output.toString();

	}
	public static  void downloadJar(String fileURL) throws  MalformedURLException,IOException{
		try {
		FileUtils.copyURLToFile(new URL(fileURL), new File("jplag-2.11.9-SNAPSHOT-jar-with-dependencies.jar"));
	} catch (IOException e) {

	}
	}}
