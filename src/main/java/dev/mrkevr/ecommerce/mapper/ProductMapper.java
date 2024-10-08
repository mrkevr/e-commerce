package dev.mrkevr.ecommerce.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import dev.mrkevr.ecommerce.dto.ProductResponse;
import dev.mrkevr.ecommerce.dto.ProductUpdateRequest;
import dev.mrkevr.ecommerce.entity.Product;

@Component
public class ProductMapper {

	public ProductResponse toResponse(Product product) {
        return ProductResponse.builder()
			.id(product.getId())
			.category(product.getCategory().getName())
			.name(product.getName())
			.description(product.getDescription())
			.currentQuantity(product.getCurrentQuantity())
			.costPrice(product.getCostPrice())
			.salePrice(product.getSalePrice())
			.image(product.getImage())
			.isActivated(product.isActivated())
			.isDeleted(product.isDeleted())
			.isOnSale(product.isOnSale())
			.created(product.getCreated())
			.modified(product.getModified())
			.build();
	}

	public List<ProductResponse> toResponse(List<Product> list) {
		return list.stream().map(this::toResponse).collect(Collectors.toList());
	}
	
	public ProductUpdateRequest toUpdateRequest(Product product) {
        return ProductUpdateRequest.builder()
			.id(product.getId())
			.categoryId(product.getCategory().getId())
			.name(product.getName())
			.description(product.getDescription())
			.currentQuantity(product.getCurrentQuantity())
			.costPrice(product.getCostPrice())
			.salePrice(product.getSalePrice())
			.build();
	}
}
