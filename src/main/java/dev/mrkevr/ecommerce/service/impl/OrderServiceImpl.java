package dev.mrkevr.ecommerce.service.impl;

import static dev.mrkevr.ecommerce.entity.OrderStatus.ACCEPTED;
import static dev.mrkevr.ecommerce.entity.OrderStatus.COMPLETED;
import static dev.mrkevr.ecommerce.entity.OrderStatus.DENIED;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.mrkevr.ecommerce.converter.LocalDateConverter;
import dev.mrkevr.ecommerce.dto.CartItemResponse;
import dev.mrkevr.ecommerce.dto.OrderMonthCount;
import dev.mrkevr.ecommerce.dto.OrderRequest;
import dev.mrkevr.ecommerce.dto.OrderResponse;
import dev.mrkevr.ecommerce.dto.OrderStatusCount;
import dev.mrkevr.ecommerce.dto.OrderUpdateRequest;
import dev.mrkevr.ecommerce.entity.CartItem;
import dev.mrkevr.ecommerce.entity.Order;
import dev.mrkevr.ecommerce.entity.OrderItem;
import dev.mrkevr.ecommerce.entity.OrderStatus;
import dev.mrkevr.ecommerce.entity.Product;
import dev.mrkevr.ecommerce.entity.ShoppingCart;
import dev.mrkevr.ecommerce.entity.User;
import dev.mrkevr.ecommerce.error.InsufficientStockError;
import dev.mrkevr.ecommerce.exception.IllegalRequestException;
import dev.mrkevr.ecommerce.exception.InsufficientStockException;
import dev.mrkevr.ecommerce.exception.OrderNotFoundException;
import dev.mrkevr.ecommerce.exception.ShoppingCartNotFoundException;
import dev.mrkevr.ecommerce.exception.UserNotFoundException;
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
	
	private final LocalDateConverter localDateConverter;
	
//	private static final List<OrderStatus> ACTIVE_STATUSES = List.of(PENDING, ACCEPTED, IN_PROGRESS, TO_SHIP, TO_RECEIVE);
//	private static final List<OrderStatus> INACTIVE_STATUSES = List.of(DENIED, CANCELLED, COMPLETED, RETURNED);
	

	@Override
	public OrderResponse getByUserIdAndOrderId(String userId, String orderId) 
	{	
		List<Order> orders = orderRepo.findActiveOrdersByUserId(userId);
		Order order = orders.stream().filter(o -> o.getId().equals(orderId)).findFirst().orElseThrow(() -> new OrderNotFoundException(orderId));
		return orderMapper.toResponse(order);
	}
	
	@Override
	@Transactional
	public OrderResponse addOrder(OrderRequest orderRequest) {
		// Fetch the User from the SecurityContext
		User user = userServ.getCurrentUser();
		
		// Fetch the cart from the User
		ShoppingCart shoppingCart = shoppingCartRepo.findById(user.getShoppingCart().getId())
				.orElseThrow(() -> new ShoppingCartNotFoundException(user.getShoppingCart().getId()));
		
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
	public List<OrderResponse> getAllActiveByUserId(String userId) {
		List<Order> orders = orderRepo.findActiveOrdersByUserId(userId);
		return orderMapper.toResponse(orders);
	}

	@Override
	public List<OrderResponse> getAllIactiveByUserId(String userId) {
		List<Order> orders = orderRepo.findInactiveOrdersByUserId(userId);
		return orderMapper.toResponse(orders);
	}

	@Override
	public List<OrderResponse> getAllOrders() {
		return orderMapper.toResponse(orderRepo.findAll());
	}
	
	@Override
	public List<OrderResponse> getAllActiveOrders() {
		List<Order> orders = orderRepo.findAllActiveOrders();
		return orderMapper.toResponse(orders);
	}

	@Override
	public List<OrderResponse> getAllInactiveOrders() {
		List<Order> orders = orderRepo.findAllInactiveOrders();
		return orderMapper.toResponse(orders);
	}

	@Override
	public List<OrderResponse> getAllByOrderStatus(OrderStatus orderStatus) {
		return orderMapper.toResponse(orderRepo.findByOrderStatus(orderStatus));
	}
	
	@Override
	public long countAllOrders() {
		return orderRepo.count();
	}

	@Override
	public long countActiveOrders() {
		return orderRepo.countActiveOrders();
	}

	@Override
	public long countInactiveOrders() {
		return orderRepo.countInactiveOrders();
	}

	@Override
	@Transactional
	public void acceptOrderById(String orderId) {
		Order order = orderRepo.findById(orderId).orElseThrow(() -> new OrderNotFoundException(orderId));
		order.setOrderStatus(ACCEPTED);
		orderRepo.save(order);
	}
	
	@Override
	public void denyOrderById(String orderId) {
		Order order = orderRepo.findById(orderId).orElseThrow(() -> new OrderNotFoundException(orderId));
		order.setOrderStatus(DENIED);
		order.setActive(false);
		orderRepo.save(order);
	}
	
	
	/**
	 * Deny, cancel or return the order by id
	 * Order's collection of {@link OrderItem} will also return to the resource, 
	 * restoring {@link Product}'s current quantity by the same amount
	 * 
	 * @param orderId Id of the {@link Order}
	 * @param orderStatus New Order's new status of enum {@link OrderStatus}
	 */
	@Override
	@Transactional
	public void cancelOrderById(String userId, String orderId) 
	{
		List<Order> orders = orderRepo.findActiveOrdersByUserId(userId);
		Order order = orders.stream().filter(o -> o.getId().equals(orderId)).findFirst().orElseThrow(() -> new OrderNotFoundException(orderId));
		
		OrderStatus orderCurrentStatus = order.getOrderStatus();
		if(orderCurrentStatus.equals(OrderStatus.ACCEPTED) ||
		   orderCurrentStatus.equals(OrderStatus.IN_PROGRESS) ||
		   orderCurrentStatus.equals(OrderStatus.TO_SHIP) ||
		   orderCurrentStatus.equals(OrderStatus.TO_RECEIVE))
		{
			throw new IllegalRequestException("Accepted orders cannot be cancelled.");
		}
		order.setOrderStatus(OrderStatus.CANCELLED);
		order.setActive(false);
		
		// Return the products back to inventory
		List<OrderItem> orderItems = order.getOrderItems();
		orderItemServ.returnOrderItem(orderItems);
		
		orderRepo.save(order);
	}
	
	@Override
	@Transactional
	public void changeOrderStatusById(String orderId, OrderStatus orderStatus) {
		Order order = orderRepo.findById(orderId).orElseThrow(() -> new OrderNotFoundException(orderId));
		
		// Complete order becomes inactive
		if(orderStatus.equals(COMPLETED)) {
			order.setActive(false);
		}
		
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
	@Transactional
	public void updateOrderById(OrderUpdateRequest request) {
		Order order = orderRepo.findById(request.getId()).orElseThrow(() -> new OrderNotFoundException(request.getId()));
		// Completed order becomes inactive
		if(request.getOrderStatus().equals(COMPLETED)) {
			order.setActive(false);
		}
		order.setOrderStatus(request.getOrderStatus());
		order.setDeliveryDate(localDateConverter.convert(request.getDeliveryDate()));
		order.setMessage(request.getMessage());
		orderRepo.save(order);
	}

	@Override
	public OrderResponse getById(String id) {
		Order order = orderRepo.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
		return orderMapper.toResponse(order);
	}

	@Override
	public List<OrderStatusCount> countOrdersByStatus() {
		
		List<OrderStatus> status = List.of(
				OrderStatus.PENDING, 
				OrderStatus.ACCEPTED,
				OrderStatus.IN_PROGRESS, 
				OrderStatus.TO_SHIP, 
				OrderStatus.TO_RECEIVE);
		
		return orderRepo.countOrdersByStatus().stream()
			.filter(o -> status.contains(o.getOrderStatus()))
			.collect(Collectors.toList());
	}

	@Override
	public List<OrderMonthCount> countOrdersByMonth(LocalDateTime dateTime, int limit) {
		return  orderRepo.countOrdersByMonth(LocalDateTime.now() , PageRequest.of(0, limit))
				.stream()
				.map(e -> new OrderMonthCount(
						Month.of(this.convertToInt(e[0])).name(), 
						this.convertToInt(e[1])))
				.collect(Collectors.toList());
	}
	
	// Helper method to conver Object to Integer
	private int convertToInt(Object o){
        String stringToConvert = String.valueOf(o);
        int output = Integer.parseInt(stringToConvert);
        return output;
    }
	
	
	//-- Helper methods --//
	
//	private boolean isActiveOrder(Order order) {
//		OrderStatus orderStatus = order.getOrderStatus();
//		return OrderStatus.activeStatuses().contains(orderStatus) && order.isActive();
//	}

}
