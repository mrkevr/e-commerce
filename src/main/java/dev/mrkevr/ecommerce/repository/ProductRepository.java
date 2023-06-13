package dev.mrkevr.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import dev.mrkevr.ecommerce.entity.Product;

public interface ProductRepository extends JpaRepository<Product, String> {

	@Query("SELECT p FROM Product p WHERE p.isDeleted = false AND p.isActivated = true")
    List<Product> findAllAvailable();

    @Query("SELECT p FROM Product p WHERE p.name LIKE %?1% OR p.description LIKE %?1%")
    List<Product> findAllByNameOrDescription(String keyword);
    
    @Query("SELECT p FROM Product p INNER JOIN Category c ON c.id = p.category.id" +
            " WHERE LOWER(p.category.name) = LOWER(?1)")
    List<Product> findAllByCategoryIgnoreCase(String category);
    
    @Query("SELECT p FROM Product p INNER JOIN Category c ON c.id = p.category.id" +
            " WHERE LOWER(p.category.name) = LOWER(?1) AND p.isActivated = true AND p.isDeleted = false")
    List<Product> findAllActivatedByCategoryIgnoreCase(String category);
   
    @Query(value = "SELECT " +
            "p.product_id, p.name, p.description, p.current_quantity, p.cost_price, p.category_id, p.sale_price, p.image, p.is_ativated, p.is_deleted " +
            "FROM products p WHERE p.is_activated = true AND p.is_deleted = false ORDER BY rand() LIMIT :limit", 
            nativeQuery = true)
    List<Product> getRandomProducts(int limit);

    @Query(value = "SELECT " +
            "p.product_id, p.name, p.description, p.current_quantity, p.cost_price, p.category_id, p.sale_price, p.image, p.is_activated, p.is_deleted " +
            "FROM products p where p.is_deleted = false and p.is_activated = true ORDER BY p.cost_price DESC LIMIT :limit", 
            nativeQuery = true)
    List<Product> filterHigherPricedProducts(int limit);

    @Query(value = "SELECT " +
            "p.product_id, p.name, p.description, p.current_quantity, p.cost_price, p.category_id, p.sale_price, p.image, p.is_activated, p.is_deleted " +
            "FROM products p WHERE p.is_deleted = false AND p.is_activated = true ORDER BY p.cost_price ASC LIMIT :limit", 
            nativeQuery = true)
    List<Product> filterLowerPricedProducts(int limit);


    @Query(value = "SELECT "
    		+ "p.product_id, p.name, p.description, p.current_quantity, p.cost_price, p.category_id, p.sale_price, p.image, p.is_activated, p.is_deleted "
    		+ "FROM products p "
    		+ "WHERE p.is_deleted = false AND p.is_activated = true LIMIT :limit", 
    		nativeQuery = true)
    List<Product> listViewProduct(int limit);

    @Query(value = "SELECT p FROM Product p INNER JOIN Category c ON c.id = :id AND p.category.id = :id "
    		+ "WHERE p.isActivated = true and p.isDeleted = false")
    List<Product> findAllByCategoryId(String id);
   
}
