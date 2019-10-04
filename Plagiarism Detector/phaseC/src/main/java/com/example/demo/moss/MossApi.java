package com.example.demo.moss;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Collection;
import java.net.URL;
import java.util.List;
import java.util.logging.Logger;

import it.zielke.moji.MossException;
import org.apache.commons.io.FileUtils;
import it.zielke.moji.SocketClient;

public class MossApi {

	private static final Logger logger = Logger.getLogger(MossApi.class.getName());

	public List<PlagiarismStorer> getPlagiarism(String filePath, String language) throws MossException {
		URL url = getMossURL(filePath, language);
		Scraper s = new Scraper();
		return s.startScraping(url.toString()+"/");
	}

	public URL getMossURL(String filePath, String language) throws MossException {
		Collection<File> files;
		if(language.equals("java"))
			files = FileUtils.listFiles(new File(filePath), new String[]{"java"}, true);
		else
			files = FileUtils.listFiles(new File(filePath), new String[]{"py"}, true);
		SocketClient socketClient = new SocketClient();

		//set your Moss user ID
		socketClient.setUserID("149201555");

		//set the programming language of all student source codes
		if(language.equals("java"))
			socketClient.setLanguage("java");
		else
			socketClient.setLanguage("python");

		//initialize connection and send parameters.
		try {
			socketClient.run();
		} catch (UnknownHostException e) {
			logger.info("UnknowsHostException.");
		} catch (IOException e) {
			logger.info("IOException");
		}

		//upload all source files of students
		for (File f : files) {
			try {
				socketClient.uploadFile(f);
			} catch (IOException e) {
				logger.info("Input output exception.");
			}
		}

		//finished uploading, tell server to check files
		try {
			socketClient.sendQuery();
		} catch (IOException e) {
			logger.info("IOException");
		}

		//get URL with Moss results and do something with it
		return socketClient.getResultURL();
	}

	public static void main(String...args) throws MossException {
		MossApi moss = new MossApi();
		URL url  = moss.getMossURL("../userdemo/test", "java");
		System.out.println(url.toString());
	}
}