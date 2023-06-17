package dev.mrkevr.ecommerce.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
	
	private String id;

	private String userId;

	private int totalItems;

	private double totalPrice;

	private List<OrderItemResponse> orderItemsResponse;
}
