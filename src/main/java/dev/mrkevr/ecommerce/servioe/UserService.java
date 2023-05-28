package dev.mrkevr.ecommerce.servioe;

import dev.mrkevr.ecommerce.dto.UserProfileResponse;

public interface UserService {
	
	UserProfileResponse getProfileResponseById(String id);
	
}
