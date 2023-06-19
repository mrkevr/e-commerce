package dev.mrkevr.ecommerce.mapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import dev.mrkevr.ecommerce.dto.CartItemResponse;
import dev.mrkevr.ecommerce.entity.CartItem;

@Component
public class CartItemMapper {
	
	public CartItemResponse toResponse(CartItem entity) {
		return CartItemResponse.builder()
			.id(entity.getId())
			.shoppingCartId(entity.getShoppingCart().getId())
			.productId(entity.getProduct().getId())
			.productName(entity.getProduct().getName())
			.quantity(entity.getQuantity())
			.unitPrice(entity.getUnitPrice())
			.build();
	}
	
	public List<CartItemResponse> toResponse(Collection<CartItem> list){
		return list.stream()
			.map(item -> this.toResponse(item))
			.collect(Collectors.toList());
	}
}
