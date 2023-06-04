package dev.mrkevr.ecommerce.servioe;

import java.util.List;

import dev.mrkevr.ecommerce.dto.CategoryResponse;
import dev.mrkevr.ecommerce.entity.Category;

public interface CategoryService {

	Category save(Category category);

	Category updateName(String id, String name);
	
	List<Category> findAllByActivatedTrue();

	List<Category> findAll();

	Category findById(String id);

	void deleteById(String id);

	Category enableById(String id);
	
	void enableAll();
	
	Category disableById(String id);

	List<CategoryResponse> getCategoriesAndSize();
}