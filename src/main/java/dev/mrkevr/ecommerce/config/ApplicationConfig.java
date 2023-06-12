package dev.mrkevr.ecommerce.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import dev.mrkevr.ecommerce.entity.Category;
import dev.mrkevr.ecommerce.entity.Role;
import dev.mrkevr.ecommerce.entity.User;
import dev.mrkevr.ecommerce.entity.embeddable.Address;
import dev.mrkevr.ecommerce.repository.ProductRepository;
import dev.mrkevr.ecommerce.repository.RoleRepository;
import dev.mrkevr.ecommerce.repository.ShoppingCartRepository;
import dev.mrkevr.ecommerce.repository.UserRepository;
import dev.mrkevr.ecommerce.servioe.ApplicationUserManager;
import dev.mrkevr.ecommerce.servioe.CategoryService;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

	private final UserRepository userRepo;
	private final RoleRepository roleRepo;
	private final ProductRepository productRepo;
	private final ShoppingCartRepository shoppingCartRepo;

	private final ApplicationUserManager applicationUserManager;
	private final PasswordEncoder passwordEncoder;
	private final CategoryService categoryServ;

	@Bean
	CommandLineRunner init() {
		return args -> {

			this.addRoles();
			this.addAdminAccount();
		};
	}

	private void addRoles() {
		if (roleRepo.count() == 0) {
			roleRepo.save(new Role("ROLE_ADMIN"));
			roleRepo.save(new Role("ROLE_USER"));
		}
	}

	private void addAdminAccount() {

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

//	@Bean
	CommandLineRunner init2() {
		return args -> {
			categoryServ.save(new Category("Electronics/Tech"));
			categoryServ.save(new Category("Cosmetics and Body Care"));
			categoryServ.save(new Category("Food and Beverage"));
			categoryServ.save(new Category("Furniture and Decor"));
			categoryServ.save(new Category("Health and Wellness"));
			categoryServ.save(new Category("Household"));
			categoryServ.save(new Category("Pet Care"));
			categoryServ.save(new Category("Office Equipment and Supplies"));
			categoryServ.save(new Category("Entertainment"));
		};
	}
}
