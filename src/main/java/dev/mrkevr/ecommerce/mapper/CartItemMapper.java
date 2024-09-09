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
			.image(entity.getProduct().getImage())
			.quantity(entity.getQuantity())
			.unitPrice(entity.getUnitPrice())
			.category(entity.getProduct().getCategory().getName())
			.build();
	}
	
	public List<CartItemResponse> toResponse(Collection<CartItem> list){
		return list.stream()
			.map(this::toResponse)
			.collect(Collectors.toList());
	}
}
