package dev.mrkevr.ecommerce.dto.validation;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MultipartFileConstraintValidator implements ConstraintValidator<MultipartFileConstraint, MultipartFile> {

	private static long MAX_FILE_SIZE;

	@Override
	public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {

//		long sizeInBytes = file.getSize();
//		long sizeInKb = sizeInBytes / 1024;
//		long sizeInMb = sizeInBytes / (1024 * 1024);
		
		long sizeInKb = file.getSize() / 1024;
		
		if (sizeInKb > MAX_FILE_SIZE) {
			return false;
		}
		return true;
	}

	@Override
	public void initialize(MultipartFileConstraint constraintAnnotation) {
		MAX_FILE_SIZE = constraintAnnotation.maximumInKb();
	}
}
