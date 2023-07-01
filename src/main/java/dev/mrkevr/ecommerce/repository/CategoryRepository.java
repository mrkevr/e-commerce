package dev.mrkevr.ecommerce.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import dev.mrkevr.ecommerce.dto.CategoryResponse;
import dev.mrkevr.ecommerce.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, String> {

	Optional<Category> findByNameIgnoreCase(String name);

	boolean existsByNameContainingIgnoreCase(String name);

	@Query("UPDATE Category c SET name = :name where id = :id")
	Category update(String name, String id);

	@Query("SELECT c from Category c WHERE c.isActivated = true")
	List<Category> findAllByActivatedTrue();
	
	@Query(value = "SELECT new dev.mrkevr.ecommerce.dto.CategoryResponse(c.id, c.name, COUNT(p.category.id), c.isActivated, c.isDeleted, c.created, c.modified) " +
            "FROM Category c LEFT JOIN Product p ON c.id = p.category.id " +
            "GROUP BY c.id")
	List<CategoryResponse> getCategoriesAndSize();	
	
	@Query(value = "SELECT new dev.mrkevr.ecommerce.dto.CategoryResponse(c.id, c.name, COUNT(p.category.id), c.isActivated, c.isDeleted, c.created, c.modified) " +
            "FROM Category c LEFT JOIN Product p ON c.id = p.category.id " +
            "WHERE c.isActivated = true AND c.isDeleted = false " +
            "GROUP BY c.id")
	List<CategoryResponse> getActiveCategoriesAndSize();	
	
	@Modifying
	@Query("UPDATE Category c SET isActivated = true, isDeleted = false")
	void enableAll();
}
