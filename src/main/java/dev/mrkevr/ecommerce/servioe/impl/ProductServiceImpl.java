package dev.mrkevr.ecommerce.servioe.impl;

import java.util.Base64;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import dev.mrkevr.ecommerce.dto.ProductRequest;
import dev.mrkevr.ecommerce.dto.ProductResponse;
import dev.mrkevr.ecommerce.entity.Product;
import dev.mrkevr.ecommerce.mapper.ProductMapper;
import dev.mrkevr.ecommerce.repository.ProductRepository;
import dev.mrkevr.ecommerce.servioe.ProductService;
import dev.mrkevr.ecommerce.utils.ImageUploader;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepo;
	private final ProductMapper productMapper;
	private final ImageUploader imageUploader;

	@Override
	public List<Product> findAll() {
		return productRepo.findAll();
	}

	@Override
	public List<ProductResponse> getAvailableProducts() {
		return productMapper.toResponse(productRepo.findAllAvailable());
	}

	@Override
	public List<ProductResponse> getAllProducts() {
		return productMapper.toResponse(productRepo.findAll());
	}

	@Override
	public ProductResponse save(ProductRequest productRequest, MultipartFile imageFile) {
		Product product = new Product();
		try {
			if (imageFile == null) {
				product.setImage(null);
			} else {
				imageUploader.uploadFile(imageFile);
				product.setImage(Base64.getEncoder().encodeToString(imageFile.getBytes()));
			}

			product.setName(productRequest.getName());
			product.setDescription(productRequest.getDescription());
			product.setCurrentQuantity(productRequest.getCurrentQuantity());
			product.setCostPrice(productRequest.getCostPrice());
			product.setCategory(productRequest.getCategory());
			product.setDeleted(false);
			product.setActivated(true);

			Product savedProduct = productRepo.save(product);
			return productMapper.toResponse(savedProduct);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public ProductResponse update(String id, ProductRequest productRequest, MultipartFile imageFile) {
		try {
			Product productToUpdate = productRepo.findById(id).orElseThrow();

			if (imageFile == null) {
				productToUpdate.setImage(productToUpdate.getImage());
			} else {
				if (imageUploader.fileExists(imageFile)) {
					productToUpdate.setImage(productToUpdate.getImage());
				} else {
					imageUploader.uploadFile(imageFile);
					productToUpdate.setImage(Base64.getEncoder().encodeToString(imageFile.getBytes()));
				}
			}

			productToUpdate.setCategory(productRequest.getCategory());
			productToUpdate.setName(productRequest.getName());
			productToUpdate.setDescription(productRequest.getDescription());
			productToUpdate.setCostPrice(productRequest.getCostPrice());
			productToUpdate.setSalePrice(productRequest.getSalePrice());
			productToUpdate.setCurrentQuantity(productRequest.getCurrentQuantity());

			Product savedProduct = productRepo.save(productToUpdate);
			return productMapper.toResponse(savedProduct);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void enableById(String id) {

		Product product = productRepo.findById(id).orElseThrow();
		product.setActivated(true);
		product.setDeleted(false);
		productRepo.save(product);
	}

	@Override
	public void deleteById(String id) {
		Product product = productRepo.findById(id).orElseThrow();
		product.setActivated(false);
		product.setDeleted(true);
		productRepo.save(product);
	}

	@Override
	public ProductResponse getById(String id) {
		Product product = productRepo.findById(id).orElseThrow();
		return productMapper.toResponse(product);
	}

	@Override
	public Product findById(String id) {
		return productRepo.findById(id).orElseThrow();
	}

	@Override
	public List<ProductResponse> randomProduct(int limit) {
		List<Product> products = productRepo.getRandomProducts(limit);
		return productMapper.toResponse(products);
	}

	@Override
	public Page<ProductResponse> searchProducts(int page, int limit, String keyword) {
		List<Product> products = productRepo.findAllByNameOrDescription(keyword);
		List<ProductResponse> dtoList = productMapper.toResponse(products);
		Pageable pageable = PageRequest.of(page, limit);
		return this.toPage(dtoList, pageable);
	}

	@Override
	public Page<ProductResponse> getAllProducts(int page, int limit) {
		List<ProductResponse> dtoList = productMapper.toResponse(productRepo.findAll());
		Pageable pageable = PageRequest.of(page, limit);
		return this.toPage(dtoList, pageable);
	}

	@Override
	public Page<ProductResponse> getAllProductsForCustomer(int page, int limit) {
		return null;
	}

	@Override
	public List<ProductResponse> findAllByCategory(String category) {
		List<Product> products = productRepo.findAllByCategoryIgnoreCase(category);
		return productMapper.toResponse(products);
	}

	@Override
	public List<ProductResponse> filterHighProducts(int limit) {
		List<Product> products = productRepo.filterHigherProducts(limit);
		return productMapper.toResponse(products);
	}

	@Override
	public List<ProductResponse> filterLowerProducts(int limit) {
		List<Product> products = productRepo.filterLowerProducts(limit);
		return productMapper.toResponse(products);
	}

	@Override
	public List<ProductResponse> listViewProducts(int limit) {
		List<Product> products = productRepo.listViewProduct(limit);
		return productMapper.toResponse(products);
	}

	@Override
	public List<ProductResponse> findByCategoryId(String id) {
		List<Product> products = productRepo.findAllByCategoryId(id);
		return productMapper.toResponse(products);
	}

	@Override
	public List<ProductResponse> searchProducts(String keyword) {
		List<Product> products = productRepo.findAllByNameOrDescription(keyword);
		return productMapper.toResponse(products);
	}

	private Page<ProductResponse> toPage(List<ProductResponse> list, Pageable pageable) {

		if (pageable.getOffset() >= list.size()) {
			return Page.empty();
		}

		int startIndex = (int) pageable.getOffset();
		int endIndex = ((pageable.getOffset() + pageable.getPageSize()) > list.size()) ? list.size()
				: (int) (pageable.getOffset() + pageable.getPageSize());
		
		List<ProductResponse> subList = list.subList(startIndex, endIndex);
		PageImpl<ProductResponse> page = new PageImpl<>(subList, pageable, list.size());
		return page;
	}

}