package dev.mrkevr.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItemResponse {

	private String id;

	private String shoppingCartId;
	
	private String productId;
	
	private String image;
	
	private String productName;
	
	private String category;
	
	private int quantity;

	private double unitPrice;
}
