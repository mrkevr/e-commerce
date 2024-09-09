package dev.mrkevr.ecommerce.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactMessageDto {

	@Size(min = 6, max = 64, message = "Name must be 6-64 characters long")
	private String name;

	@Email(
		message = "Must be a valid email",
		regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" 
	        + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")
	private String email;

	@Size(min = 6, max = 64, message = "Phone must be 6-64 characters long")
	private String phone;

	@Size(min = 12, message = "Message must be at least 12 characters long")
	private String message;
}
