package dev.mrkevr.ecommerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.mrkevr.ecommerce.entity.User;

public interface UserRepository extends JpaRepository<User, String> {
	
	Optional<User> findByEmail(String email);
	
	Optional<User> findByUsername(String username);
	
	Optional<User> findByOauth2Id(String id);
	
	boolean existsByEmail(String email);
	
	boolean existsByUsername(String username);
	
}
