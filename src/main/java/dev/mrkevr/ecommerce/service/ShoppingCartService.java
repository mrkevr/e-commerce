package dev.mrkevr.ecommerce.service;

import java.util.List;

import dev.mrkevr.ecommerce.dto.ShoppingCartResponse;
import dev.mrkevr.ecommerce.entity.ShoppingCart;
import dev.mrkevr.ecommerce.error.InsufficientStockError;

public interface ShoppingCartService {
	
	ShoppingCartResponse getById(String id);
	
	ShoppingCartResponse getByUserId(String id);
	
	ShoppingCartResponse addCartItem(String userId, String productId, int quantity);

	ShoppingCartResponse updateCartItem(String userId, String cartItemId, int quantity);

	ShoppingCartResponse deleteCartItem(String userId, String cartItemId);

	void clearShoppingCart(ShoppingCart shoppingCart);
	
	List<InsufficientStockError> checkProductAvailability(ShoppingCart shoppingCart);	
}
