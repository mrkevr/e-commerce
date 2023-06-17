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

	private String productName;

	private int quantity;
	
	private double unitPrice;
}
