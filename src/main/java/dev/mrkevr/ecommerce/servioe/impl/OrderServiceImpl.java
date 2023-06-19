package dev.mrkevr.ecommerce.servioe.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.mrkevr.ecommerce.dto.CartItemResponse;
import dev.mrkevr.ecommerce.dto.OrderRequest;
import dev.mrkevr.ecommerce.dto.OrderResponse;
import dev.mrkevr.ecommerce.entity.CartItem;
import dev.mrkevr.ecommerce.entity.Order;
import dev.mrkevr.ecommerce.entity.OrderItem;
import dev.mrkevr.ecommerce.entity.ShoppingCart;
import dev.mrkevr.ecommerce.entity.User;
import dev.mrkevr.ecommerce.exception.IllegalRequestException;
import dev.mrkevr.ecommerce.exception.ShoppingCartNotFoundException;
import dev.mrkevr.ecommerce.exception.UserNotFoundException;
import dev.mrkevr.ecommerce.mapper.CartItemMapper;
import dev.mrkevr.ecommerce.mapper.OrderItemMapper;
import dev.mrkevr.ecommerce.mapper.OrderMapper;
import dev.mrkevr.ecommerce.repository.OrderRepository;
import dev.mrkevr.ecommerce.repository.ProductRepository;
import dev.mrkevr.ecommerce.repository.ShoppingCartRepository;
import dev.mrkevr.ecommerce.repository.UserRepository;
import dev.mrkevr.ecommerce.servioe.ApplicationUserManager;
import dev.mrkevr.ecommerce.servioe.OrderService;
import dev.mrkevr.ecommerce.servioe.ShoppingCartService;
import dev.mrkevr.ecommerce.servioe.UserService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {
	
	private final UserRepository userRepo;
	private final ProductRepository productRepo;
	private final ShoppingCartRepository shoppingCartRepo;
	private final OrderRepository orderRepo;
	
	private final ApplicationUserManager userManager;
	private final ShoppingCartService shoppingCartServ;
	private final UserService userServ;
	
	private final CartItemMapper cartItemMapper;
	private final OrderMapper orderMapper;
	private final OrderItemMapper orderItemMapper;
	
	/**
	 * Shows preview of the User's shopping cart before checking it out and become order/order items
	 * 
	 * @return Instance of {@link OrderRequest}
	 */
	@Override
	public OrderRequest previewOrderRequest() {
		
		User user = userServ.getCurrentUser();
		ShoppingCart shoppingCart = shoppingCartRepo.findByUser(user).orElseThrow(() -> new ShoppingCartNotFoundException());
		
		List<CartItemResponse> cartItems = shoppingCart.getCartItems()
			.stream()
			.map(item -> cartItemMapper.toResponse(item))
			.collect(Collectors.toList());
		
		return OrderRequest.builder()
			.userId(user.getId())
			.shoppingCartId(shoppingCart.getId())
			.cartItems(cartItems)
			.totalItems(shoppingCart.getTotalItems())
			.totalPrice(shoppingCart.getTotalPrice())
			.message("")
			.paymentMethod("")
			.deliveryAddress("")
			.build();
	}

	@Transactional
	@Override
	public OrderResponse addOrder(OrderRequest orderRequest) {
		
		User user = userServ.getCurrentUser();
//		if(user.getId() != orderRequest.getUserId()) {
//			throw new IllegalRequestException("Current user didn't match the order request");
//		}
		
		ShoppingCart shoppingCart = user.getShoppingCart();
		if(shoppingCart == null || shoppingCart.getCartItems() == null || shoppingCart.getCartItems().isEmpty()) {
			throw new IllegalRequestException("User's shopping cart is empty.");
		}
		
		Set<CartItem> cartItems = shoppingCart.getCartItems();
		List<OrderItem> orderItems = orderItemMapper.toEntity(cartItems);
		
		
		List<Order> orders = user.getOrders();
		if(orders == null) orders = new ArrayList<>();
		
		Order order = new Order();
		order.setUser(user);
		order.setOrderItems(orderItems);
		orderItems.forEach(item -> item.setOrder(order));
		order.setTotalItems(shoppingCart.getTotalItems());
		order.setTotalPrice(shoppingCart.getTotalPrice());
		order.setDeliveryAddress(orderRequest.getDeliveryAddress());
		order.setMessage(orderRequest.getMessage());
		order.setPaymentMethod(orderRequest.getPaymentMethod());
		
		// Saving the order
		Order savedOrder = orderRepo.save(order);
		
		
		cartItems.clear();
		shoppingCart.setCartItems(cartItems);
		shoppingCart.setTotalItems(0);
		shoppingCart.setTotalPrice(0);
		shoppingCartRepo.save(shoppingCart);
		
		
		return orderMapper.toResponse(savedOrder);
	}
	
	private void clearShoppingCart() {
		
	}
}
