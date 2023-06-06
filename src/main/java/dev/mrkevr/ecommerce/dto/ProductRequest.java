package dev.mrkevr.ecommerce.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {

	@Size(min = 6, max = 50, message = "Name must be 6-50 characters long.")
	private String name;

	@NotBlank
	private String categoryId;

	@Size(min = 6, max = 120, message = "Description must be 6-120 characters long.")
	private String description;
	
	private String image;

	@Min(value = 1, message = "Quantity must be 1 to 100,000.")
	@Max(value = 100_000, message = "Quantity must be 1 to 100,000.")
	private int currentQuantity;

	@DecimalMin(value = "1.00", message = "Cost price must be 1-1,000,000.00.")
	@DecimalMax(value = "1000000", message = "Cost price must be 1-1,000,000.00.")
	private Double costPrice;
}
