package dev.mrkevr.ecommerce.servioe;

import org.springframework.security.provisioning.UserDetailsManager;

import dev.mrkevr.ecommerce.dto.UserProfileDto;
import dev.mrkevr.ecommerce.dto.UserRegistrationRequest;

public interface ApplicationUserManager extends UserDetailsManager {

	UserProfileDto registerUser(UserRegistrationRequest userRegistrationRequest);
	
	UserProfileDto updateUser(UserProfileDto dto);

	boolean emailAlreadyTaken(String email);

	boolean usernameAlreadyTaken(String username);
	
	void deleteUserById(String id);
}
