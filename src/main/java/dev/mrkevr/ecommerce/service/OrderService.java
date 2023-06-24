package dev.mrkevr.ecommerce.service;

import java.time.LocalDate;
import java.util.List;

import dev.mrkevr.ecommerce.dto.OrderRequest;
import dev.mrkevr.ecommerce.dto.OrderResponse;
import dev.mrkevr.ecommerce.entity.Order;
import dev.mrkevr.ecommerce.entity.OrderStatus;

public interface OrderService {
	
	OrderRequest previewOrderRequest();
	
	List<OrderResponse> getAllByUserId(String userId);
	
	List<OrderResponse> getAllActiveByUserId(String userId);
	
	List<OrderResponse> getAllIactiveByUserId(String userId);
	
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
	
	void changeOrderStatusById(String orderId, OrderStatus orderStatus);
	
	void changeDeliveryDateById(String orderId, LocalDate date);
	
	void cancelOrderById(String orderId, OrderStatus orderStatus);
}
