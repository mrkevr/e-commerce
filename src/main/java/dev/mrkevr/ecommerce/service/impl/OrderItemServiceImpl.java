package dev.mrkevr.ecommerce.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.mrkevr.ecommerce.entity.CartItem;
import dev.mrkevr.ecommerce.entity.OrderItem;
import dev.mrkevr.ecommerce.entity.Product;
import dev.mrkevr.ecommerce.exception.InsufficientStockException;
import dev.mrkevr.ecommerce.repository.ProductRepository;
import dev.mrkevr.ecommerce.service.OrderItemService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderItemServiceImpl implements OrderItemService {

	private final ProductRepository productRepository;
	
	/*
	 * Convert cart item to order item
	 * Process includes deducting product's quantity by the requested quantity
	 */
	@Override
	@Transactional
	public OrderItem processCartItem(CartItem cartItem) {
		
		int currentQuantity = cartItem.getProduct().getCurrentQuantity();
		int requestedQuantity = cartItem.getQuantity();
		
		if(currentQuantity <  requestedQuantity) {
			throw new InsufficientStockException(cartItem.getProduct().getId());
		}
		
		OrderItem orderItem = OrderItem.builder()
			.product(cartItem.getProduct())
			.quantity(cartItem.getQuantity())
			.unitPrice(cartItem.getUnitPrice())
			.build();
		
		Product product = cartItem.getProduct();
		product.setCurrentQuantity(product.getCurrentQuantity() - requestedQuantity);
		productRepository.save(product);
		
		return orderItem;
	}

	@Override
	@Transactional
	public List<OrderItem> processCartItem(Collection<CartItem> cartItems) {
		
		return cartItems.stream().map(item -> this.processCartItem(item)).collect(Collectors.toList());
	}

}
