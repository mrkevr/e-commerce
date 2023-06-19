package dev.mrkevr.ecommerce.servioe;

import java.util.List;

import dev.mrkevr.ecommerce.dto.UserProfileResponse;
import dev.mrkevr.ecommerce.entity.User;

public interface UserService {
	
	UserProfileResponse getProfileDto(String id);
	
	List<UserProfileResponse> getAllUsersDto();
	
	User getCurrentUser();
}
