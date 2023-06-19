package dev.mrkevr.ecommerce.mapper;

import org.springframework.stereotype.Component;

import dev.mrkevr.ecommerce.dto.OrderResponse;
import dev.mrkevr.ecommerce.entity.Order;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OrderMapper {

	private final OrderItemMapper orderItemMapper;

	public OrderResponse toResponse(Order entity) {

		return OrderResponse.builder()
			.id(entity.getId())
			.userId(entity.getUser().getId())
			.orderItemsResponse(orderItemMapper.toResponse(entity.getOrderItems()))
			.totalItems(entity.getTotalItems())
			.totalPrice(entity.getTotalPrice())
			.build();
	}
}
