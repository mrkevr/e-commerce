package dev.mrkevr.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import dev.mrkevr.ecommerce.entity.Order;
import dev.mrkevr.ecommerce.entity.OrderStatus;

public interface OrderRepository extends JpaRepository<Order, String> {
	
	@Query("SELECT o FROM Order o WHERE o.user.id = :userId")
	List<Order> findOrdersByUserId(String userId);
	
	List<Order> findByOrderStatusIn(List<OrderStatus> orderStatuses);
	
	List<Order> findByOrderStatus(OrderStatus orderStatus);
}
