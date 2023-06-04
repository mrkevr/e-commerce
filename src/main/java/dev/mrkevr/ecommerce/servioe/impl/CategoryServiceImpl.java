package dev.mrkevr.ecommerce.servioe.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.mrkevr.ecommerce.dto.CategoryResponse;
import dev.mrkevr.ecommerce.entity.Category;
import dev.mrkevr.ecommerce.exception.CategoryNotFoundException;
import dev.mrkevr.ecommerce.repository.CategoryRepository;
import dev.mrkevr.ecommerce.servioe.CategoryService;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
	
	private final CategoryRepository categoryRepo;
	
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
		Category updatedCategory = categoryRepo.save(toUpdate);
		return updatedCategory;
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
	
	// do not use, use disableById method instead
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
		Category toDisable = categoryRepo.findById(id).orElseThrow(() -> new CategoryNotFoundException(id));
		toDisable.setActivated(false);
		toDisable.setDeleted(true);
		return categoryRepo.save(toDisable);
	}

	@Override
	public List<CategoryResponse> getCategoriesAndSize() {
		return categoryRepo.getCategoriesAndSize();
	}

	



	

}
