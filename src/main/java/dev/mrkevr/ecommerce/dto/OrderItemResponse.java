package dev.mrkevr.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemResponse {
	
	private String id;

	private String orderId;
	
	private String productId;
	
	private String productName;
	
	private String category;
	
	private String image;

	private int quantity;
	
	private double unitPrice;
}
