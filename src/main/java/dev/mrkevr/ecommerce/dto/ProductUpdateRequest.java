package dev.mrkevr.ecommerce.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductUpdateRequest {
	
	private String id;
	
	@Size(min = 6, max = 150, message = "Name must be 6-150 characters long.")
	private String name;

	@NotBlank(message = "Please choose a category.")
	private String categoryId;

	@Size(min = 10, max = 1500, message = "Description must be 10-1500 characters long.")
	private String description;
	
	@Min(value = 1, message = "Quantity must be 1 to 100,000.")
	@Max(value = 100_000, message = "Quantity must be 1 to 100,000.")
	private int currentQuantity;

	@DecimalMin(value = "1.00", message = "Cost price must be 1-1,000,000.00.")
	@DecimalMax(value = "1000000", message = "Cost price must be 1-1,000,000.00.")
	@NotNull(message = "Cost price must be 1-1,000,000.00.")
	private Double costPrice;
	
	@DecimalMin(value = "1.00", message = "Sale price must be 1-1,000,000.00.")
	@DecimalMax(value = "1000000", message = "Sale price must be 1-1,000,000.00.")
	@NotNull(message = "Sale price must be 1-1,000,000.00.")
	private Double salePrice;
}
