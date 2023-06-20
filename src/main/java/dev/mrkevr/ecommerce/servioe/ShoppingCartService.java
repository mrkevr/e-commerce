package dev.mrkevr.ecommerce.servioe;

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

	ShoppingCartResponse addItemToCartSession(ShoppingCartResponse cartDto, String productId, int quantity);

	ShoppingCartResponse updateCartSession(ShoppingCartResponse cartDto, String productId, int quantity);

	ShoppingCartResponse removeItemFromCartSession(ShoppingCartResponse cartDto, String productId, int quantity);
	
	void clearShoppingCart(ShoppingCart shoppingCart);
	
	List<InsufficientStockError> checkProductAvailability(ShoppingCart shoppingCart);
	
//	ShoppingCart combineCart(ShoppingCartDto cartDto, ShoppingCart cart);

//	void deleteCartById(Long id);

}
