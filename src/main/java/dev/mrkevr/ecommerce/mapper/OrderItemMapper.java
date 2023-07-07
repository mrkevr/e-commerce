package dev.mrkevr.ecommerce.mapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import dev.mrkevr.ecommerce.dto.OrderItemResponse;
import dev.mrkevr.ecommerce.entity.CartItem;
import dev.mrkevr.ecommerce.entity.OrderItem;

@Component
public class OrderItemMapper {
	
	public OrderItemResponse toResponse(OrderItem orderItem) {
		return OrderItemResponse.builder()
			.id(orderItem.getId())
			.orderId(orderItem.getOrder().getId())
			.productId(orderItem.getProduct().getId())
			.productName(orderItem.getProduct().getName())
			.image(orderItem.getProduct().getImage())
			.quantity(orderItem.getQuantity())
			.unitPrice(orderItem.getUnitPrice())
			.build();
	}
	
	public List<OrderItemResponse> toResponse(Collection<OrderItem> orderItems){
		return orderItems.stream()
			.map(item -> this.toResponse(item))
			.collect(Collectors.toList());
	}
	
	public OrderItem toEntity(CartItem cartItem) {
		return OrderItem.builder()
			.product(cartItem.getProduct())
			.quantity(cartItem.getQuantity())
			.unitPrice(cartItem.getUnitPrice())
			.build();
	}
	
	public List<OrderItem> toEntity(Collection<CartItem> cartItems){
		return cartItems.stream()
			.map(item -> this.toEntity(item))
			.collect(Collectors.toList());
	}
}
