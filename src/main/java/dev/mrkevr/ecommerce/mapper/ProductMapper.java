package dev.mrkevr.ecommerce.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import dev.mrkevr.ecommerce.dto.ProductResponse;
import dev.mrkevr.ecommerce.entity.Product;

@Component
public class ProductMapper {

	public ProductResponse toResponse(Product product) {
		ProductResponse response = new ProductResponse();
		response.setId(product.getId());
		response.setName(product.getName());
		response.setCurrentQuantity(product.getCurrentQuantity());
		response.setCostPrice(product.getCostPrice());
		response.setSalePrice(product.getSalePrice());
		response.setDescription(product.getDescription());
		response.setImage(product.getImage());
		response.setCategory(product.getCategory());
		response.setActivated(product.isActivated());
		response.setDeleted(product.isDeleted());
		return response;
	}

	public List<ProductResponse> toResponse(List<Product> list) {
		return list.stream().map(product -> this.toResponse(product)).collect(Collectors.toList());
	}
	
	
}
