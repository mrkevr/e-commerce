package dev.mrkevr.ecommerce.dto.validation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Retention(RUNTIME)
@Target(FIELD)
@Constraint(validatedBy = UsernameConstraintValidator.class)
public @interface UsernameConstraint {
	
	String message() default "Username must be 6-12 characters long, can only start with a letter, and has no special characters.";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
