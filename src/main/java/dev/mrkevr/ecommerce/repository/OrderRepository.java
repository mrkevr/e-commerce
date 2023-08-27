package dev.mrkevr.ecommerce.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import dev.mrkevr.ecommerce.dto.OrderStatusCount;
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
	
	@Query("SELECT new dev.mrkevr.ecommerce.dto.OrderStatusCount(o.orderStatus, COUNT(o)) " +
	           "FROM Order o " +
			   "GROUP BY o.orderStatus")
	List<OrderStatusCount> countOrdersByStatus();
	
	/**
	 * Return the month number and its count its Order
	 * 
	 * @param dateTime instance of {@link LocalDateTime}, the query will return all the data from the beginning of time up to the startDate
	 * @param pageable instance of {@link Pageable} for pagination
	 */
	@Query("SELECT MONTH(o.created) as month, COUNT(o) as orderCount " +
		       "FROM Order o " +
		       "WHERE o.created <= :startDate " +
		       "GROUP BY MONTH(o.created) " +
		       "ORDER BY MONTH(o.created) ASC")
	Page<Object[]> countOrdersByMonth(@Param("startDate") LocalDateTime dateTime, Pageable pageable);
}
