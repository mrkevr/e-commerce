
package dev.mrkevr.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.mrkevr.ecommerce.entity.ShoppingCart;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, String> {

}
