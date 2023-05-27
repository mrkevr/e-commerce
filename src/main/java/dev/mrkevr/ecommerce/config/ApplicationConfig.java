package dev.mrkevr.ecommerce.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import dev.mrkevr.ecommerce.entity.Role;
import dev.mrkevr.ecommerce.entity.User;
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

			if (roleRepo.count() == 0) {
				roleRepo.save(new Role("ADMIN"));
				roleRepo.save(new Role("USER"));
			}

			if (!userRepo.existsByUsername("admin")) {
				User user = new User();
				user.setEmail("admin@e-commerce.com");
				user.setUsername("admin");
				user.setPassword(passwordEncoder.encode("admin"));
				user.getRoles().add(roleRepo.findByRoleIgnoreCase("admin").get());
				
				userRepo.save(user);
			}
		};
	}
}
