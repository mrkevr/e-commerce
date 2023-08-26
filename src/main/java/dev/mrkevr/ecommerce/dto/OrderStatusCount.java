package dev.mrkevr.ecommerce.dto;

import dev.mrkevr.ecommerce.entity.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderStatusCount {
	
	private OrderStatus orderStatus;
	private Long orderCount;
	
}
