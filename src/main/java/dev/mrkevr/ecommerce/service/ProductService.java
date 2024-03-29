package dev.mrkevr.ecommerce.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import dev.mrkevr.ecommerce.dto.ProductRequest;
import dev.mrkevr.ecommerce.dto.ProductResponse;
import dev.mrkevr.ecommerce.dto.ProductUpdateRequest;
import dev.mrkevr.ecommerce.entity.Product;

public interface ProductService {

	List<ProductResponse> findAll();
	
	List<ProductResponse> getAvailableProductsOnSale();;

	List<ProductResponse> getAvailableProducts();

	List<ProductResponse> getAllProducts();

	ProductResponse save(ProductRequest productRequest, MultipartFile imageFile);

	ProductResponse update(String id, ProductUpdateRequest productRequest, MultipartFile imageFile);

	ProductResponse enableById(String id);

	ProductResponse disableById(String id);
	
	ProductResponse putOnSaleById(String id);
	
	ProductResponse putOffSaleById(String id);
	
	ProductResponse getById(String id);
	
	ProductUpdateRequest getUpdateRequestById(String id);
	
	Product findById(String id);

	List<ProductResponse> getRandomProducts(int limit);
	
	List<ProductResponse> getRandomProductsByCategoryId(String id, int limit);

	Page<ProductResponse> searchProductsByKeyword(int page, int limit, String keyword);

	Page<ProductResponse> getAll(int page, int limit);

	Page<ProductResponse> getAllProductsForCustomer(int page, int limit);

	List<ProductResponse> getAllByCategory(String category);
	
	List<ProductResponse> getAllActivatedByCategory(String category);
	
	List<ProductResponse> filterHighProducts(int limit);

	List<ProductResponse> filterLowerProducts(int limit);
	
	List<ProductResponse> getNewlyAddedProducts(int limit);

	List<ProductResponse> listViewProducts(int limit);

	List<ProductResponse> findByCategoryId(String id);

	List<ProductResponse> searchProducts(String keyword);
}
