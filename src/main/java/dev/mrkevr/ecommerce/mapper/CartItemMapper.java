package dev.mrkevr.ecommerce.mapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import dev.mrkevr.ecommerce.dto.CartItemResponse;
import dev.mrkevr.ecommerce.entity.CartItem;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CartItemMapper {
	
	private final ProductMapper productMapper;
	private final ShoppingCartMapper shoppingCartMapper;
	
	public CartItemResponse toResponse(CartItem entity) {
		return CartItemResponse.builder()
			.id(entity.getId())
			.shoppingCartResponse(shoppingCartMapper.toResponse(entity.getShoppingCart()))
			.productResponse(productMapper.toResponse(entity.getProduct()))
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
