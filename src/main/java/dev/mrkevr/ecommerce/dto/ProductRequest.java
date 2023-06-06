package dev.mrkevr.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {

	private String name;

	private String categoryId;
	
	private String description;
	
	private String image;
	
	private int currentQuantity;

	private double costPrice;

	private double salePrice;
}
