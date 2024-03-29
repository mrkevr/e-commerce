package dev.mrkevr.ecommerce.service;

import java.util.List;

import org.springframework.security.core.Authentication;

import dev.mrkevr.ecommerce.dto.LoggedInUserDetails;
import dev.mrkevr.ecommerce.dto.UserProfileResponse;
import dev.mrkevr.ecommerce.entity.User;

public interface UserService {
	
	UserProfileResponse getProfileDto(String id);
	
	List<UserProfileResponse> getAllUsersDto();
	
	User getCurrentUser();
	
	UserProfileResponse getCurrentUserProfileResponse();
	
	LoggedInUserDetails getLoggedInUserDetails(Authentication authentication);
	
	LoggedInUserDetails getLoggedInUserDetailsByUserId(String id);
	
	LoggedInUserDetails getLoggedInUserDetailsByUser(User user);
}
