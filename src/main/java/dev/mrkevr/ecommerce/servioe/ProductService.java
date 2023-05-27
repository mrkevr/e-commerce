package dev.mrkevr.ecommerce.servioe;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import dev.mrkevr.ecommerce.dto.ProductRequest;
import dev.mrkevr.ecommerce.dto.ProductResponse;
import dev.mrkevr.ecommerce.entity.Product;

public interface ProductService {

	List<Product> findAll();

	List<ProductResponse> getAvailableProducts();

	List<ProductResponse> getAllProducts();

	ProductResponse save(ProductRequest productRequest, MultipartFile imageFile);

	ProductResponse update(long id, ProductRequest productRequest, MultipartFile imageFile);

	void enableById(Long id);

	void deleteById(Long id);

	ProductResponse getById(Long id);

	Product findById(Long id);

	List<ProductResponse> randomProduct(int limit);

	Page<ProductResponse> searchProducts(int page, int limit, String keyword);

	Page<ProductResponse> getAllProducts(int page, int limit);

	Page<ProductResponse> getAllProductsForCustomer(int page, int limit);

	List<ProductResponse> findAllByCategory(String category);

	List<ProductResponse> filterHighProducts(int limit);

	List<ProductResponse> filterLowerProducts(int limit);

	List<ProductResponse> listViewProducts(int limit);

	List<ProductResponse> findByCategoryId(Long id);

	List<ProductResponse> searchProducts(String keyword);
}
