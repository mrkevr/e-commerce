package dev.mrkevr.ecommerce.servioe;

import java.util.List;

import dev.mrkevr.ecommerce.dto.UserProfileDto;

public interface UserService {
	
	UserProfileDto getProfileDto(String id);
	
	List<UserProfileDto> getAllUsersDto();
}
