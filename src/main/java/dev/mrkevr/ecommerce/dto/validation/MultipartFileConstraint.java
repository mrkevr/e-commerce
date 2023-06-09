package dev.mrkevr.ecommerce.dto.validation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Retention(RUNTIME)
@Target({FIELD, PARAMETER})
@Constraint(validatedBy = MultipartFileConstraintValidator.class)
public @interface MultipartFileConstraint {
	
	String message() default "File is too large.";
	
	long maximumInKb() default 500;
	
	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
