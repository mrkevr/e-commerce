package dev.mrkevr.ecommerce.dto;

import java.time.LocalDate;
import java.util.List;

import dev.mrkevr.ecommerce.entity.OrderStatus;
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

	private List<OrderItemResponse> orderItems;
	
	private int totalItems;

	private double totalPrice;
	
	private boolean isAccepted;
	
	private OrderStatus orderStatus;
	
	private String message;
	
	private LocalDate orderDate;
	
	private LocalDate deliveryDate;
	
	private String deliveryAddress;
	
	private String paymentMethod;
}
