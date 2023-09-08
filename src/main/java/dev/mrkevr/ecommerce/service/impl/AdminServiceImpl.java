package dev.mrkevr.ecommerce.service.impl;

import org.springframework.stereotype.Service;

import dev.mrkevr.ecommerce.repository.CategoryRepository;
import dev.mrkevr.ecommerce.repository.OrderRepository;
import dev.mrkevr.ecommerce.repository.ProductRepository;
import dev.mrkevr.ecommerce.repository.UserRepository;
import dev.mrkevr.ecommerce.service.AdminService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

	private final UserRepository userRepo;
	private final CategoryRepository categoryRepo;
	private final ProductRepository productRepo;
	private final OrderRepository orderRepo;

	@Override
	public long getTotalUsers() {
		return userRepo.count();
	}

	@Override
	public long getTotalCategories() {
		return categoryRepo.count();
	}

	@Override
	public long getTotalProducts() {
		return productRepo.count();
	}

	@Override
	public long getTotalOrders() {
		return orderRepo.count();
	}

	@Override
	public long getTotalActiveOrders() {
		return orderRepo.countActiveOrders();
	}
}
