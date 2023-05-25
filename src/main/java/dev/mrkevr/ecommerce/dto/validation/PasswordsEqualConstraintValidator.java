package dev.mrkevr.ecommerce.dto.validation;

import dev.mrkevr.ecommerce.dto.UserRegistrationRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordsEqualConstraintValidator implements ConstraintValidator<PasswordsEqualConstraint, Object> {

	@Override
	public boolean isValid(Object candidate, ConstraintValidatorContext context) {
		UserRegistrationRequest userRegistrationRequest = (UserRegistrationRequest) candidate;
		return userRegistrationRequest.getPassword().equals(userRegistrationRequest.getConfirmPassword());
	}
}
