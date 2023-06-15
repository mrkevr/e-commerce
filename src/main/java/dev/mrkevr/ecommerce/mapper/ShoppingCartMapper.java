package dev.mrkevr.ecommerce.mapper;


import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import dev.mrkevr.ecommerce.dto.ShoppingCartResponse;
import dev.mrkevr.ecommerce.entity.ShoppingCart;

@Component
public class ShoppingCartMapper {
	
	private final UserMapper userMapper;
	private final CartItemMapper cartItemMapper;
	
	public ShoppingCartMapper(UserMapper userMapper, @Lazy CartItemMapper cartItemMapper) {
		this.userMapper = userMapper;
		this.cartItemMapper = cartItemMapper;
	}

	public ShoppingCartResponse toResponse(ShoppingCart entity) {
		
		return ShoppingCartResponse.builder()
			.id(entity.getId())
			.userProfileResponse(userMapper.toUserProfileResponse(entity.getUser()))
			.cartItemResponses(cartItemMapper.toResponse(entity.getCartItems()))
			.totalItems(entity.getTotalItems())
			.totalPrice(entity.getTotalPrice())
			.build();
	}
	
	
	
	
	
}
