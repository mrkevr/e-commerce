package dev.mrkevr.ecommerce.servioe;

import java.util.List;
import java.util.Optional;

import dev.mrkevr.ecommerce.dto.CategoryResponse;
import dev.mrkevr.ecommerce.entity.Category;

public interface CategoryService {

	Category save(Category category);

	Category update(Category category);

	List<Category> findAllByActivatedTrue();

	List<Category> findALl();

	Optional<Category> findById(String id);

	void deleteById(String id);

	void enableById(String id);

	List<CategoryResponse> getCategoriesAndSize();
}