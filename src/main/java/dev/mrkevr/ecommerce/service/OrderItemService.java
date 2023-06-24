package dev.mrkevr.ecommerce.service;

import java.util.Collection;
import java.util.List;

import dev.mrkevr.ecommerce.entity.CartItem;
import dev.mrkevr.ecommerce.entity.OrderItem;

public interface OrderItemService {

	OrderItem processCartItem(CartItem cartItem);

	List<OrderItem> processCartItem(Collection<CartItem> cartItems);
}
