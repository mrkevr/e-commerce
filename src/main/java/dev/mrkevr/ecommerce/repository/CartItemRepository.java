package dev.mrkevr.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.mrkevr.ecommerce.entity.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, String> {

}
