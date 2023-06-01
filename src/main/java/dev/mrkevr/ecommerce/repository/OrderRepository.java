package dev.mrkevr.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import dev.mrkevr.ecommerce.entity.Order;

public interface OrderRepository extends JpaRepository<Order, String> {
	
	@Query("SELECT o FROM Order o WHERE o.user.id = :userId")
	List<Order> findOrdersByUserId(String userId);
}
