
package dev.mrkevr.ecommerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.mrkevr.ecommerce.entity.ShoppingCart;
import dev.mrkevr.ecommerce.entity.User;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, String> {
	
	Optional<ShoppingCart> findByUser(User user);
	
}
