package dev.mrkevr.ecommerce.service;

import java.time.LocalDate;
import java.util.List;

import dev.mrkevr.ecommerce.dto.OrderRequest;
import dev.mrkevr.ecommerce.dto.OrderResponse;
import dev.mrkevr.ecommerce.dto.OrderUpdateRequest;
import dev.mrkevr.ecommerce.entity.OrderStatus;

public interface OrderService {
	
	List<OrderResponse> getAllByUserId(String userId);
	
	List<OrderResponse> getAllActiveByUserId(String userId);
	
	List<OrderResponse> getAllIactiveByUserId(String userId);
	
	OrderResponse getByUserIdAndOrderId(String userId, String orderId);
	
	OrderResponse addOrder(OrderRequest orderRequest);
	
	List<OrderResponse> getAllOrders();
	
	List<OrderResponse> getAllByOrderStatus(OrderStatus orderStatus);
	
	List<OrderResponse> getAllActiveOrders();
	
	List<OrderResponse> getAllInactiveOrders();
	
	long countAllOrders();
	
	long countActiveOrders();
	
	long countInactiveOrders();
	
	OrderResponse getById(String id);
	
	void acceptOrderById(String orderId);
	
	void denyOrderById(String orderId);
	
	void changeOrderStatusById(String orderId, OrderStatus orderStatus);
	
	void changeDeliveryDateById(String orderId, LocalDate date);
	
	void updateOrderById(OrderUpdateRequest request);
	
	void cancelOrderById(String userId, String orderID);
}
