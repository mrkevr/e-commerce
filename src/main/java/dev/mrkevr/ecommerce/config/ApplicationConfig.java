package dev.mrkevr.ecommerce.config;

import java.util.function.Consumer;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import dev.mrkevr.ecommerce.dto.OrderRequest;
import dev.mrkevr.ecommerce.entity.Category;
import dev.mrkevr.ecommerce.entity.Role;
import dev.mrkevr.ecommerce.entity.User;
import dev.mrkevr.ecommerce.entity.embeddable.Address;
import dev.mrkevr.ecommerce.repository.CategoryRepository;
import dev.mrkevr.ecommerce.repository.OrderRepository;
import dev.mrkevr.ecommerce.repository.ProductRepository;
import dev.mrkevr.ecommerce.repository.RoleRepository;
import dev.mrkevr.ecommerce.repository.ShoppingCartRepository;
import dev.mrkevr.ecommerce.repository.UserRepository;
import dev.mrkevr.ecommerce.service.ApplicationUserManager;
import dev.mrkevr.ecommerce.service.CategoryService;
import dev.mrkevr.ecommerce.service.OrderService;
import dev.mrkevr.ecommerce.service.ProductService;
import dev.mrkevr.ecommerce.service.ShoppingCartService;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

	private final UserRepository userRepo;
	private final RoleRepository roleRepo;
	private final CategoryRepository categoryRepo;
	private final ProductRepository productRepo;
	private final ShoppingCartRepository shoppingCartRepo;
	private final OrderRepository orderRepo;

	private final ApplicationUserManager applicationUserManager;
	private final ProductService productServ;
	private final CategoryService categoryServ;
	private final ShoppingCartService shoppingCartServ;
	private final OrderService orderServ;
	
	private final PasswordEncoder passwordEncoder;

	
	@Bean
	CommandLineRunner init() {
		return args -> {
			this.initialData();
			
			shoppingCartServ.addCartItem("USER-6461-4920", "PROD-8972-5753", 1);
			
		};
	}
	
	private void initialData() {
		this.addAdminAccountIfNotExist();
		this.addRolesIfNotExists();
		this.addCategoriesIfEmpty();
	}
	
	private void addCategoriesIfEmpty() {
		if (categoryRepo.count() == 0) {
			categoryServ.save(new Category("Bags and Accessories"));
			categoryServ.save(new Category("Children's Clothing and Accessories"));
			categoryServ.save(new Category("Consumer Electronics"));
			categoryServ.save(new Category("Cosmetics and Body Care"));
			categoryServ.save(new Category("Food and Beverage"));
			categoryServ.save(new Category("Furniture and Decor"));
			categoryServ.save(new Category("Health and Wellness"));
			categoryServ.save(new Category("Household"));
			categoryServ.save(new Category("Jewelries and Accessories"));
			categoryServ.save(new Category("Media"));
			categoryServ.save(new Category("Men's Clothing and Accessories"));
			categoryServ.save(new Category("Office and School Supplies"));
			categoryServ.save(new Category("Pet Care"));
			categoryServ.save(new Category("Service"));
			categoryServ.save(new Category("Women's Clothing and Accessories"));
		}
	}

	private void addRolesIfNotExists() {
		if (roleRepo.count() == 0) {
			roleRepo.save(new Role("ROLE_ADMIN"));
			roleRepo.save(new Role("ROLE_USER"));
		}
	}

	private void addAdminAccountIfNotExist() {

		if (!userRepo.existsByUsername("admin")) {
			User user = new User();
			user.setUsername("admin");
			user.setEmail("admin@e-commerce.com");
			user.setAddress(new Address("B1 L26 Dollar St. Camella Homes", "Banlic", "Cabuyao", "Laguna", "4025"));
			user.setPhone("1234-12345");
			user.setPassword(passwordEncoder.encode("admin"));
			user.getRoles().add(roleRepo.findByRoleIgnoreCase("ROLE_ADMIN").get());
			userRepo.save(user);
		}
	}
	
	private Consumer<Object> print(){
		return args -> {
			System.out.println(args);
		};
	}
}
