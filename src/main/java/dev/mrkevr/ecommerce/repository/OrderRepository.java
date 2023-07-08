package dev.mrkevr.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import dev.mrkevr.ecommerce.entity.Order;
import dev.mrkevr.ecommerce.entity.OrderStatus;

public interface OrderRepository extends JpaRepository<Order, String> {
		
	@Query("SELECT o FROM Order o WHERE o.user.id = :userId")
	List<Order> findOrdersByUserId(String userId);
	
	List<Order> findByOrderStatus(OrderStatus orderStatus);
	
	List<Order> findByOrderStatusIn(List<OrderStatus> orderStatuses);
	
	@Query("SELECT o FROM Order o WHERE o.isActive = TRUE")
	List<Order> findAllActiveOrders();
	
	@Query("SELECT o FROM Order o WHERE o.isActive = FALSE")
	List<Order> findAllInactiveOrders();
	
	@Query("SELECT COUNT(o) FROM Order o WHERE o.isActive = TRUE")
	long countActiveOrders();
	
	@Query("SELECT COUNT(o) FROM Order o WHERE o.isActive = FALSE")
	long countInactiveOrders();
	
	@Query("SELECT COUNT(o) FROM Order o WHERE o.user.id = :userId AND o.isActive = TRUE")
	long countActiveOrdersByUserId(String userId);
	
	@Query("SELECT o FROM Order o WHERE o.user.id = :userId AND o.isActive = TRUE")
	List<Order> findActiveOrdersByUserId(String userId);
	
	@Query("SELECT o FROM Order o WHERE o.user.id = :userId AND o.isActive = FALSE")
	List<Order> findInactiveOrdersByUserId(String userId);
}
