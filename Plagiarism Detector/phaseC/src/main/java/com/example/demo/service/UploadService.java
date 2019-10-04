package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * This class represents a service to upload files into the system
 * 
 * @author sandipsamal
 *
 */
@Service("UploadService")
public class UploadService {
	private static final String PATH = "submissions/";
	private static final Logger LOGGER = Logger.getLogger(UploadService.class.getName());

	/**
	 * Uploads a list of files into the system
	 * 
	 * @param files
	 *            : a list of files
	 * @param assignmentName
	 *            : an assignment name
	 * @param studentName
	 *            : a student name
	 * @param courseName
	 *            : a course name
	 * @param semName
	 *            : a semester name
	 * @param subId
	 *            : a submission id
	 * @return : true iff file upload is successful
	 */
	public boolean uploadFiles(MultipartFile[] files, String assignmentName, String studentName, String courseName,
			String semName, String subId) {

		String dirPath = String.format("%s%s/%s/%s/%s/%s", PATH, semName, courseName, assignmentName, studentName,
				subId);
		LOGGER.info("Im in!");
		LOGGER.info(dirPath);
		boolean uploadStatus = false;
		File folder1 = new File(PATH);
		if (!folder1.exists()) {
			folder1.mkdir();
		}
		File folder = new File(dirPath);
		if (!folder.exists()) {
			folder.mkdirs();
		}

		for (MultipartFile file : files) {
			String filePath = String.format("%s/%s", dirPath, file.getOriginalFilename());
			File convFile = new File(filePath);
			try {
				boolean created = convFile.createNewFile();
				if (!created) {
					continue;
				}

			} catch (IOException e) {
				return uploadStatus;
			}
			try (FileOutputStream fos = new FileOutputStream(convFile);) {
				fos.write(files[0].getBytes());
			} catch (Exception e) {
				return uploadStatus;
			}
		}

		uploadStatus = true;
		return uploadStatus;
	}
}
