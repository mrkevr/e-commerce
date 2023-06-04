package dev.mrkevr.ecommerce.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryUpdateDto {
	
	private String id;

	@NotBlank(message = "Category name must not be blank.")
	@Length(min = 6, max = 50, message = "Category name must be 6 - 60 characters.")
	private String name;
}

