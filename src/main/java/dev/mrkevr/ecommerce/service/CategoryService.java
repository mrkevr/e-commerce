package dev.mrkevr.ecommerce.service;

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
	
	Category disableById(String id);
	
	void enableAll();

	List<CategoryResponse> getCategoriesAndSize();
}