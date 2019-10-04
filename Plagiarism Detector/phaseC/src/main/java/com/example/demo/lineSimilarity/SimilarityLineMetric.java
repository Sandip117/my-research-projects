package com.example.demo.lineSimilarity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class SimilarityLineMetric {
	ClassLoader classLoader = getClass().getClassLoader();

	private static Map<Integer, String> populateMap(File f1) throws IOException{
		Map<Integer, String> pythonLines = new HashMap<>();
		int lineNumber = 0;

		FileInputStream fis = new FileInputStream(f1);
		//Construct BufferedReader from InputStreamReader
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));

		String line = null;
		while ((line = br.readLine()) != null) {
			line = line.replaceAll("\\s+","");
			pythonLines.put(++lineNumber, line);
		}
		br.close();
		return pythonLines;
	}

	private static Object getKeyFromValue(Map hm, Object value) {
		for (Object o : hm.keySet()) {
			if (hm.get(o).equals(value)) {
				return o;
			}
		}
		return null;
	}
	private static int populateList(Map<Integer, String> allLinesF1, Map<Integer, String> allLinesF2) throws IOException {

		List<Integer> plagarisedLineNumberF1 = new ArrayList<Integer>();
		List<Integer> plagarisedLineNumberF2 = new ArrayList<Integer>();
		Iterator<String> it = allLinesF1.values().iterator();
		while(it.hasNext()) {
			String val = it.next();

			if(!(val.startsWith("#") || val.equals(""))) {
				if(allLinesF2.containsValue(val)) {
					Integer s1 = (Integer)getKeyFromValue(allLinesF1, val);
					Integer s2 = (Integer)getKeyFromValue(allLinesF2, val);
					plagarisedLineNumberF1.add(s1);
					plagarisedLineNumberF2.add(s2);
					it.remove();

				}

			}
		}
		return plagarisedLineNumberF1.size();
	}


	public int similar(File f1, File f2) throws IOException {
		Map<Integer, String> mapf1 = null;
		Map<Integer, String> mapf2 = null;
		mapf1 = populateMap(f1);
		mapf2 = populateMap(f2);
		return populateList(mapf1, mapf2);
	}
	public static void main(String...args) throws IOException {
		SimilarityLineMetric sim = new SimilarityLineMetric();
		File f1 = new File(sim.classLoader.getResource("a1/simple.py").getFile());
		File f2 = new File(sim.classLoader.getResource("a2/simple.py").getFile());
		System.out.println(sim.similar(f1, f2));
	}
}
