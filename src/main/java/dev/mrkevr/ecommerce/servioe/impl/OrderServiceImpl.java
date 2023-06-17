package dev.mrkevr.ecommerce.servioe.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.mrkevr.ecommerce.dto.CartItemResponse;
import dev.mrkevr.ecommerce.dto.OrderRequest;
import dev.mrkevr.ecommerce.entity.ShoppingCart;
import dev.mrkevr.ecommerce.entity.User;
import dev.mrkevr.ecommerce.exception.ShoppingCartNotFoundException;
import dev.mrkevr.ecommerce.exception.UserNotFoundException;
import dev.mrkevr.ecommerce.mapper.CartItemMapper;
import dev.mrkevr.ecommerce.repository.OrderRepository;
import dev.mrkevr.ecommerce.repository.ShoppingCartRepository;
import dev.mrkevr.ecommerce.repository.UserRepository;
import dev.mrkevr.ecommerce.servioe.OrderService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {
	
	private final UserRepository userRepo;
	private final ShoppingCartRepository shoppingCartRepo;
	private final OrderRepository orderRepo;
	private final CartItemMapper cartItemMapper;
	
	/**
	 * Shows preview of the shopping cart before checking out
	 * 
	 * @param userId Id of {@link User}
	 * @param shoppingCartId Id of {@link ShoppingCart}
	 * 
	 * @return Instance of {@link OrderRequest}
	 */
	@Override
	public OrderRequest previewOrderRequest(String userId, String shoppingCartId) 
	{
		User user = userRepo.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
		ShoppingCart shoppingCart = user.getShoppingCart();
		if(shoppingCart == null) throw new ShoppingCartNotFoundException();
		
		List<CartItemResponse> cartItems = shoppingCart.getCartItems()
			.stream()
			.map(item -> cartItemMapper.toResponse(item))
			.collect(Collectors.toList());
		
		return OrderRequest.builder()
			.userId(userId)
			.shoppingCartId(shoppingCartId)
			.cartItems(cartItems)
			.message("")
			.paymentMethod("")
			.deliveryAddress("")
			.build();
	}
}
