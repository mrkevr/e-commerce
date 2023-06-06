package dev.mrkevr.ecommerce.servioe;

import org.springframework.security.provisioning.UserDetailsManager;

import dev.mrkevr.ecommerce.dto.UserProfileResponse;
import dev.mrkevr.ecommerce.dto.UserRegistrationRequest;

public interface ApplicationUserManager extends UserDetailsManager {

	UserProfileResponse registerUser(UserRegistrationRequest userRegistrationRequest);
	
	UserProfileResponse updateUser(UserProfileResponse dto);

	boolean emailAlreadyTaken(String email);

	boolean usernameAlreadyTaken(String username);
	
	void deleteUserById(String id);
}
