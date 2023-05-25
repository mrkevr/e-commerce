package dev.mrkevr.ecommerce.servioe;

import org.springframework.security.provisioning.UserDetailsManager;

import dev.mrkevr.ecommerce.dto.UserRegistrationRequest;
import dev.mrkevr.ecommerce.entity.User;

public interface ApplicationUserManager extends UserDetailsManager {

	User registerUser(UserRegistrationRequest userRegistrationRequest);

	boolean emailAlreadyTaken(String email);

	boolean usernameAlreadyTaken(String username);
	
	void deleteUserById(Long id);
}
