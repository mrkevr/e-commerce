package dev.mrkevr.ecommerce.dto;

import dev.mrkevr.ecommerce.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {

	private String name;

	private String description;

	private int currentQuantity;

	private double costPrice;

	private double salePrice;

	private String image;

	private Category category;
}
