package dev.mrkevr.ecommerce.servioe;

import dev.mrkevr.ecommerce.dto.ShoppingCartResponse;

public interface ShoppingCartService {
	
	ShoppingCartResponse getById(String id);
	
	ShoppingCartResponse getByUserId(String id);
	
	ShoppingCartResponse addCartItem(String userId, String productId, int quantity);

	ShoppingCartResponse updateCart(String userId, String productId, int quantity);

	ShoppingCartResponse removeItemFromCart(String userId, String productId);

	ShoppingCartResponse addItemToCartSession(ShoppingCartResponse cartDto, String productId, int quantity);

	ShoppingCartResponse updateCartSession(ShoppingCartResponse cartDto, String productId, int quantity);

	ShoppingCartResponse removeItemFromCartSession(ShoppingCartResponse cartDto, String productId, int quantity);

//	ShoppingCart combineCart(ShoppingCartDto cartDto, ShoppingCart cart);

//	void deleteCartById(Long id);

	
}
