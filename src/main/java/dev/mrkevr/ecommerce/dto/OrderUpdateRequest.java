package dev.mrkevr.ecommerce.dto;

import org.springframework.format.annotation.DateTimeFormat;

import dev.mrkevr.ecommerce.entity.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderUpdateRequest {
	
	private String id;
	
	private OrderStatus orderStatus;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private String deliveryDate;
}
