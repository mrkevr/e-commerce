package dev.mrkevr.ecommerce.mapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import dev.mrkevr.ecommerce.dto.OrderItemResponse;
import dev.mrkevr.ecommerce.entity.OrderItem;

@Component
public class OrderItemMapper {
	
	public OrderItemResponse toResponse(OrderItem entity) {
		return OrderItemResponse.builder()
			.id(entity.getId())
			.orderId(entity.getOrder().getId())
			.productId(entity.getProduct().getId())
			.productName(entity.getProduct().getName())
			.quantity(entity.getQuantity())
			.unitPrice(entity.getUnitPrice())
			.build();
	}
	
	public List<OrderItemResponse> toResponse(Collection<OrderItem> list){
		return list.stream()
			.map(item -> this.toResponse(item))
			.collect(Collectors.toList());
	}
}
