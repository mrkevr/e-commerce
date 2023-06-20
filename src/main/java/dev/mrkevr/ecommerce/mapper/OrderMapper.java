package dev.mrkevr.ecommerce.mapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import dev.mrkevr.ecommerce.dto.OrderResponse;
import dev.mrkevr.ecommerce.entity.Order;
import dev.mrkevr.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OrderMapper {

	private final OrderItemMapper orderItemMapper;
	private final UserRepository userRepo;
	
	public OrderResponse toResponse(Order order) {
		return OrderResponse.builder()
			.id(order.getId())
			.userId(order.getUser().getId())
			.orderItemsResponse(orderItemMapper.toResponse(order.getOrderItems()))
			.totalItems(order.getTotalItems())
			.totalPrice(order.getTotalPrice())
			.orderDate(order.getOrderDate())
			.deliveryDate(order.getDeliveryDate() == null ? order.getDeliveryDate() : null)
			.deliveryAddress(order.getDeliveryAddress())
			.paymentMethod(order.getPaymentMethod())
			.orderStatus(order.getOrderStatus())
			.message(order.getMessage())
			.build();
	}
	
	public List<OrderResponse> toResponse(Collection<Order> orders){
		return orders.stream()
			.map(order -> this.toResponse(order))
			.collect(Collectors.toList());
	}
}
