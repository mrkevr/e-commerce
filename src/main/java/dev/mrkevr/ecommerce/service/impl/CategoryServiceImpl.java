package dev.mrkevr.ecommerce.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.mrkevr.ecommerce.dto.CategoryResponse;
import dev.mrkevr.ecommerce.entity.Category;
import dev.mrkevr.ecommerce.exception.CategoryNotFoundException;
import dev.mrkevr.ecommerce.repository.CategoryRepository;
import dev.mrkevr.ecommerce.repository.ProductRepository;
import dev.mrkevr.ecommerce.service.CategoryService;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
	
	private final CategoryRepository categoryRepo;
	private final ProductRepository productRepo;
	
	@Override
	@Transactional
	public Category save(Category category) {
		return categoryRepo.save(category);
	}

	@Override
	@Transactional
	public Category updateName(String id, String name) {
		Category toUpdate = categoryRepo.findById(id).orElseThrow(() -> new CategoryNotFoundException(id));
		toUpdate.setName(name);
        return categoryRepo.save(toUpdate);
	}
	
	@Override
	public List<Category> findAllByActivatedTrue() {
		return categoryRepo.findAllByActivatedTrue();
	}

	@Override
	public List<Category> findAll() {
		return categoryRepo.findAll();
	}

	@Override
	public Category findById(String id) {
		return categoryRepo.findById(id).orElseThrow(() -> new CategoryNotFoundException(id));
	}
	
	// Do not use, use disableById method instead
	@Override
	@Transactional
	public void deleteById(String id) {
		Category toDelete = categoryRepo.findById(id).orElseThrow(() -> new CategoryNotFoundException(id));
		categoryRepo.delete(toDelete);
	}

	@Override
	@Transactional
	public Category enableById(String id) {
		Category toEnable = categoryRepo.findById(id).orElseThrow(() -> new CategoryNotFoundException(id));
		toEnable.setActivated(true);
		toEnable.setDeleted(false);
		return categoryRepo.save(toEnable);
	}
	
	@Override
	@Transactional
	public void enableAll() {
		categoryRepo.enableAll();
	}
	
	@Override
	@Transactional
	public Category disableById(String id) {
		Category toDisable = categoryRepo.findById(id)
				.orElseThrow(() -> new CategoryNotFoundException(id));
		toDisable.setActivated(false);
		toDisable.setDeleted(true);
		return categoryRepo.save(toDisable);
	}

	@Override
	public List<CategoryResponse> getActiveCategoriesAndSize() {
		return categoryRepo.getActiveCategoriesAndSize();
	}

	@Override
	public List<CategoryResponse> getCategoriesAndSize() {
		return categoryRepo.getCategoriesAndSize();
	}

	@Override
	public List<CategoryResponse> getAvailableCategories() {
		// Fetch all categories with at least 1 product
		List<CategoryResponse> categories = categoryRepo.getActiveCategoriesAndSize().stream()
			.filter(c -> c.getCount() > 0)
			.collect(Collectors.toList());
		
		// Assign each category with a random image related to the category
		categories.forEach(c -> {
			String image = productRepo.getRandomProductsByCategoryId(c.getId(), 1).get(0).getImage();
			c.setImage(image);
		});
		 
		return categories;
	}
}
