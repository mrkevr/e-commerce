package dev.mrkevr.ecommerce.servioe;

import org.springframework.security.provisioning.UserDetailsManager;

import dev.mrkevr.ecommerce.dto.UserProfileResponse;
import dev.mrkevr.ecommerce.dto.UserRegistrationRequest;
import dev.mrkevr.ecommerce.entity.User;

public interface ApplicationUserManager extends UserDetailsManager {

	UserProfileResponse registerUser(UserRegistrationRequest userRegistrationRequest);
	
	User registerUser(User user);
	
	UserProfileResponse updateUser(UserProfileResponse dto);

	boolean emailAlreadyTaken(String email);

	boolean usernameAlreadyTaken(String username);
	
	void deleteUserById(String id);
}
