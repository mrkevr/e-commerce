package dev.mrkevr.ecommerce.mapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import dev.mrkevr.ecommerce.dto.OrderResponse;
import dev.mrkevr.ecommerce.entity.Order;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OrderMapper {

	private final OrderItemMapper orderItemMapper;
	
	public OrderResponse toResponse(Order order) {
		return OrderResponse.builder()
			.id(order.getId())
			.userId(order.getUser().getId())
			.orderItems(orderItemMapper.toResponse(order.getOrderItems()))
			.totalItems(order.getTotalItems())
			.totalPrice(order.getTotalPrice())
			.isActive(order.isActive())
			.orderDate(order.getOrderDate())
			.deliveryDate(order.getDeliveryDate() != null ? order.getDeliveryDate() : null)
			.deliveryAddress(order.getDeliveryAddress())
			.paymentMethod(order.getPaymentMethod())
			.orderStatus(order.getOrderStatus())
			.message(order.getMessage())
			.build();
	}
	
	public List<OrderResponse> toResponse(Collection<Order> orders){
		return orders.stream()
			.map(this::toResponse)
			.collect(Collectors.toList());
	}
}
