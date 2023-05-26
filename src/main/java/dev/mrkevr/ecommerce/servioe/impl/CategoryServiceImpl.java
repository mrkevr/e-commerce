package dev.mrkevr.ecommerce.servioe.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import dev.mrkevr.ecommerce.dto.CategoryResponse;
import dev.mrkevr.ecommerce.entity.Category;
import dev.mrkevr.ecommerce.repository.CategoryRepository;
import dev.mrkevr.ecommerce.servioe.CategoryService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
	
	private final CategoryRepository categoryRepo;
	
	@Override
	public Category save(Category category) {
		return categoryRepo.save(new Category(category.getName()));
	}

	@Override
	public Category update(Category category) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Category> findAllByActivatedTrue() {
		return categoryRepo.findAllByActivatedTrue();
	}

	@Override
	public List<Category> findALl() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Category> findById(Long id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void enableById(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<CategoryResponse> getCategoriesAndSize() {
		
		return categoryRepo.getCategoriesAndSize();
	}

}
