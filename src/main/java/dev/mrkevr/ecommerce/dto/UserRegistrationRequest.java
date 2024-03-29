package dev.mrkevr.ecommerce.dto;

import org.hibernate.validator.constraints.Length;

import dev.mrkevr.ecommerce.dto.validation.PasswordsEqualConstraint;
import dev.mrkevr.ecommerce.dto.validation.UsernameConstraint;
import dev.mrkevr.ecommerce.entity.embeddable.Address;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@PasswordsEqualConstraint
public class UserRegistrationRequest {
	
	@UsernameConstraint
	private String username;
	
	@Size(min = 3, max = 24, message = "First Name must be 3-24 characters long.")
	private String firstName;
	
	@Size(min = 3, max = 24, message = "Last Name must be 3-24 characters long.")
	private String lastName;
	
	@Valid
	@NotNull(message = "Address must not be null.")
	private Address address;
	
	@Email
	@NotBlank(message = "Email must not be blank.")
	private String email;
	
	@Length(min = 8,max = 45, message = "Phone must be 8-45 characters long.")
	@NotBlank(message = "Phone must not be blank.")
	private String phone;
	
	@Size(min = 6, max = 24, message = "Password must 6-24 characters long.")
	private String password;
	
	@Size(min = 6, max = 24, message = "Password must 6-24 characters long.")
	private String confirmPassword;
}
