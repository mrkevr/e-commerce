package dev.mrkevr.ecommerce.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {

	private String id;

	private String name;

	private String description;

	private String category;

	private String image;

	private int currentQuantity;
	
	private double costPrice;
	
	private double salePrice;

	private boolean isOnSale;

	private boolean isActivated;

	private boolean isDeleted;
	
	private LocalDateTime created;

	private LocalDateTime modified;
}
