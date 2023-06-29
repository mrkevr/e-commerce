package dev.mrkevr.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import dev.mrkevr.ecommerce.entity.CartItem;
import dev.mrkevr.ecommerce.entity.User;

public interface CartItemRepository extends JpaRepository<CartItem, String> {
	
	@Query("SELECT COUNT(i) FROM CartItem i WHERE i.shoppingCart.user = :user")
	long countAllByUser(User user);
	
	@Query("SELECT COUNT(i) FROM CartItem i WHERE i.shoppingCart.user.id = :userId")
	long countAllByUserId(String userId);
}
