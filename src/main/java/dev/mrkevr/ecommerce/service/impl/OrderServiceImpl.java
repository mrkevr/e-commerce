package dev.mrkevr.ecommerce.service.impl;

import static dev.mrkevr.ecommerce.entity.OrderStatus.ACCEPTED;
import static dev.mrkevr.ecommerce.entity.OrderStatus.CANCELLED;
import static dev.mrkevr.ecommerce.entity.OrderStatus.COMPLETED;
import static dev.mrkevr.ecommerce.entity.OrderStatus.DENIED;
import static dev.mrkevr.ecommerce.entity.OrderStatus.IN_PROGRESS;
import static dev.mrkevr.ecommerce.entity.OrderStatus.PENDING;
import static dev.mrkevr.ecommerce.entity.OrderStatus.RETURNED;
import static dev.mrkevr.ecommerce.entity.OrderStatus.TO_RECEIVE;
import static dev.mrkevr.ecommerce.entity.OrderStatus.TO_SHIP;

import java.time.LocalDate;
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
import dev.mrkevr.ecommerce.entity.OrderStatus;
import dev.mrkevr.ecommerce.entity.ShoppingCart;
import dev.mrkevr.ecommerce.entity.User;
import dev.mrkevr.ecommerce.error.InsufficientStockError;
import dev.mrkevr.ecommerce.exception.IllegalRequestException;
import dev.mrkevr.ecommerce.exception.InsufficientStockException;
import dev.mrkevr.ecommerce.exception.OrderNotFoundException;
import dev.mrkevr.ecommerce.exception.ShoppingCartNotFoundException;
import dev.mrkevr.ecommerce.mapper.CartItemMapper;
import dev.mrkevr.ecommerce.mapper.OrderItemMapper;
import dev.mrkevr.ecommerce.mapper.OrderMapper;
import dev.mrkevr.ecommerce.repository.OrderRepository;
import dev.mrkevr.ecommerce.repository.ProductRepository;
import dev.mrkevr.ecommerce.repository.ShoppingCartRepository;
import dev.mrkevr.ecommerce.repository.UserRepository;
import dev.mrkevr.ecommerce.service.ApplicationUserManager;
import dev.mrkevr.ecommerce.service.OrderItemService;
import dev.mrkevr.ecommerce.service.OrderService;
import dev.mrkevr.ecommerce.service.ShoppingCartService;
import dev.mrkevr.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {
	
	private final UserRepository userRepo;
	private final ProductRepository productRepo;
	private final ShoppingCartRepository shoppingCartRepo;
	private final OrderRepository orderRepo;
	
	private final UserService userServ;
	private final ApplicationUserManager userManager;
	private final ShoppingCartService shoppingCartServ;
	private final OrderItemService orderItemServ;
	
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

	@Override
	@Transactional
	public OrderResponse addOrder(OrderRequest orderRequest) {
		
		// Fetch the User from the SecurityContext
		User user = userServ.getCurrentUser();
		
		// Fetch the cart from the User
		ShoppingCart shoppingCart = user.getShoppingCart();
		if(shoppingCart == null || shoppingCart.getCartItems() == null || shoppingCart.getCartItems().isEmpty()) {
			throw new IllegalRequestException("User cannot check out, shopping cart is empty.");
		}
		
		/*
		 * Check for the availability of the product(s) being ordered
		 * Throws an exception with the list of the insufficient products
		 */
		List<InsufficientStockError> stockErrors = shoppingCartServ.checkProductAvailability(shoppingCart);
		if(!stockErrors.isEmpty()) {
			throw new InsufficientStockException(stockErrors);
		}
		
		// Fetch the cart items from shopping cart
		Set<CartItem> cartItems = shoppingCart.getCartItems();
		
		// Convert cart items to collection of order items
//		List<OrderItem> orderItems = orderItemMapper.toEntity(cartItems);
		List<OrderItem> orderItems = orderItemServ.processCartItem(cartItems);
		
		
		// Fetch order list from the user
		List<Order> orders = user.getOrders();
		// Create new if it doesn't exist
		if(orders == null) orders = new ArrayList<>();
		
		// Creating the order
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
	
	@Override
	public List<OrderResponse> getAllByUserId(String userId) {
		List<Order> orders = orderRepo.findOrdersByUserId(userId);
		return orderMapper.toResponse(orders);
	}

	@Override
	public List<OrderResponse> getAllOrders() {
		return orderMapper.toResponse(orderRepo.findAll());
	}
	
	@Override
	public List<OrderResponse> getAllActiveOrders() {
		List<OrderStatus> activeStatuses = List.of(PENDING, ACCEPTED, IN_PROGRESS, TO_SHIP, TO_RECEIVE);
		return orderMapper.toResponse(orderRepo.findByOrderStatusIn(activeStatuses));
	}

	@Override
	public List<OrderResponse> getAllInactiveOrders() {
		List<OrderStatus> inactiveStatuses = List.of(CANCELLED, DENIED, COMPLETED, RETURNED);
		return orderMapper.toResponse(orderRepo.findByOrderStatusIn(inactiveStatuses));
	}

	@Override
	public List<OrderResponse> getAllByOrderStatus(OrderStatus orderStatus) {
		return orderMapper.toResponse(orderRepo.findByOrderStatus(orderStatus));
	}

	@Override
	@Transactional
	public void acceptOrderById(String orderId) {
		Order order = orderRepo.findById(orderId).orElseThrow(() -> new OrderNotFoundException(orderId));
		order.setAccepted(true);
		order.setOrderStatus(ACCEPTED);
		orderRepo.save(order);
	}

	@Override
	@Transactional
	public void denyOrderById(String orderId) {
		Order order = orderRepo.findById(orderId).orElseThrow(() -> new OrderNotFoundException(orderId));
		order.setAccepted(false);
		order.setOrderStatus(DENIED);
		orderRepo.save(order);
	}

	@Override
	@Transactional
	public void changeOrderStatusById(String orderId, OrderStatus orderStatus) {
		Order order = orderRepo.findById(orderId).orElseThrow(() -> new OrderNotFoundException(orderId));
		order.setOrderStatus(orderStatus);
		orderRepo.save(order);
	}

	@Override
	@Transactional
	public void changeDeliveryDateById(String orderId, LocalDate date) {
		Order order = orderRepo.findById(orderId).orElseThrow(() -> new OrderNotFoundException(orderId));
		order.setDeliveryDate(date);
		orderRepo.save(order);
	}

	@Override
	public OrderResponse getById(String id) {
		Order order = orderRepo.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
		return orderMapper.toResponse(order);
	}
}
