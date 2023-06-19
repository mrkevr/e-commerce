package dev.mrkevr.ecommerce.servioe;

import dev.mrkevr.ecommerce.dto.ShoppingCartResponse;
import dev.mrkevr.ecommerce.entity.ShoppingCart;

public interface ShoppingCartService {
	
	ShoppingCartResponse getById(String id);
	
	ShoppingCartResponse getByUserId(String id);
	
	ShoppingCartResponse addCartItem(String userId, String productId, int quantity);

	ShoppingCartResponse updateCartItem(String userId, String cartItemId, int quantity);

	ShoppingCartResponse deleteCartItem(String userId, String cartItemId);

	ShoppingCartResponse addItemToCartSession(ShoppingCartResponse cartDto, String productId, int quantity);

	ShoppingCartResponse updateCartSession(ShoppingCartResponse cartDto, String productId, int quantity);

	ShoppingCartResponse removeItemFromCartSession(ShoppingCartResponse cartDto, String productId, int quantity);
	
	void clearShoppingCart(ShoppingCart shoppingCart);
	
//	ShoppingCart combineCart(ShoppingCartDto cartDto, ShoppingCart cart);

//	void deleteCartById(Long id);

}
