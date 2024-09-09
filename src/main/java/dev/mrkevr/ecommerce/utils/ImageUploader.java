package dev.mrkevr.ecommerce.utils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@Slf4j
public class ImageUploader {

	private final String UPLOAD_FOLDER_PATH = "D:\\Programming Applications\\eclipse-workspace\\e-commerce\\src\\main\\resources\\static\\img\\products";

	public boolean uploadFile(MultipartFile file, String fileName) {
		boolean uploadSuccess = false;
		try {
			Files.copy(
				file.getInputStream(), 
				Paths.get(UPLOAD_FOLDER_PATH + File.separator + fileName + this.getFileExtension(file.getOriginalFilename())), StandardCopyOption.REPLACE_EXISTING);
			uploadSuccess = true;
		} catch (Exception e) {
			log.warn("Error occurred when uploading file", e.getMessage());
		}

		return uploadSuccess;
	}

	public boolean fileExists(MultipartFile multipartFile) {
		boolean isExist = false;

		try {
			File file = new File(UPLOAD_FOLDER_PATH + "\\" + multipartFile.getOriginalFilename());
			isExist = file.exists();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return isExist;
	}

	/**
	   * Removes all characters before the last 'DOT' from the name.
	   * @param name as the file name
	   * @return the extension of the file.
	   */
	public String getFileExtension(String name) {
		String extension;
		try {
			extension = name.substring(name.lastIndexOf("."));
		} catch (Exception e) {
			extension = "";
		}
		return extension;
	}
}