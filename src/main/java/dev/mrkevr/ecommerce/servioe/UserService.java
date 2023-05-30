package dev.mrkevr.ecommerce.servioe;

import dev.mrkevr.ecommerce.dto.UserProfileDto;

public interface UserService {
	
	UserProfileDto getProfileDto(String id);
	
}
