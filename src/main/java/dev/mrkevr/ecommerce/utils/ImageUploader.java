package dev.mrkevr.ecommerce.utils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ImageUploader {

	private final String UPLOAD_FOLDER = "D:\\Programming Applications\\eclipse-workspace\\e-commerce\\src\\main\\resources\\static\\img";

	public boolean uploadFile(MultipartFile file) {
		boolean uploadSuccess = false;
		try {
			Files.copy(file.getInputStream(), 
				Paths.get(UPLOAD_FOLDER + File.separator + file.getOriginalFilename()),
				StandardCopyOption.REPLACE_EXISTING);
			uploadSuccess = true;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return uploadSuccess;
	}

	public boolean fileExists(MultipartFile multipartFile) {
		boolean isExist = false;
		try {
			File file = new File(UPLOAD_FOLDER + "\\" + multipartFile.getOriginalFilename());
			isExist = file.exists();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isExist;
	}
}