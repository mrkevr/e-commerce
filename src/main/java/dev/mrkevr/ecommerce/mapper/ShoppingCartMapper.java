package dev.mrkevr.ecommerce.mapper;

import org.springframework.stereotype.Component;

import dev.mrkevr.ecommerce.dto.ShoppingCartResponse;
import dev.mrkevr.ecommerce.entity.ShoppingCart;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ShoppingCartMapper {

	private final CartItemMapper cartItemMapper;

	public ShoppingCartResponse toResponse(ShoppingCart entity) {
		return ShoppingCartResponse.builder()
			.id(entity.getId())
			.userId(entity.getUser().getId())
			.cartItems(cartItemMapper.toResponse(entity.getCartItems()))
			.totalItems(entity.getTotalItems())
			.totalPrice(entity.getTotalPrice())
			.build();
	}
}
