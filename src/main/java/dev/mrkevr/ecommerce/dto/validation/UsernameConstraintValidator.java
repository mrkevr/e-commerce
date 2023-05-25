package dev.mrkevr.ecommerce.dto.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UsernameConstraintValidator implements ConstraintValidator<UsernameConstraint, String> {

	@Override
	public boolean isValid(String username, ConstraintValidatorContext context) {
		
		username = username.trim();
		// blank/null check
		if (username == null || username == "") {
			return false;
		}
		// size check
		if (username.length() < 6 || username.length() > 12) {
			return false;
		}
		// username must start with a character
		if (!Character.isLetter(username.charAt(0))) {
			return false;
		}
		// username must contain only upper and lowercase letters, numbers or underscore
		Pattern pattern = Pattern.compile("^[a-zA-Z0-9_]*$");
		Matcher matcher = pattern.matcher(username);
		if(!matcher.find()) {
			return false;
		}
		return true;
	}
}
