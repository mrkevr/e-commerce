package dev.mrkevr.ecommerce.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {

	private String id;

	private String name;

	private String description;

	private int currentQuantity;

	private double costPrice;

	private double salePrice;

	private String image;

	private String category;

	private boolean isActivated;

	private boolean isDeleted;
	
	private LocalDateTime created;

	private LocalDateTime modified;
}
