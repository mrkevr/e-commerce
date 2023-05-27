package dev.mrkevr.ecommerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.mrkevr.ecommerce.entity.Role;

public interface RoleRepository extends JpaRepository<Role, String> {

	Optional<Role> findByRoleIgnoreCase(String role);
}
