package dev.mrkevr.ecommerce.servioe.impl;

import org.springframework.stereotype.Service;

import dev.mrkevr.ecommerce.dto.UserProfileResponse;
import dev.mrkevr.ecommerce.entity.User;
import dev.mrkevr.ecommerce.exception.UserNotFoundException;
import dev.mrkevr.ecommerce.mapper.UserMapper;
import dev.mrkevr.ecommerce.repository.UserRepository;
import dev.mrkevr.ecommerce.servioe.UserService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	
	private final UserRepository userRepo;
	private final UserMapper userMapper;
	
	@Override
	public UserProfileResponse getProfileResponseById(String id) {
		User user = userRepo.findById(id)
			.orElseGet(() -> userRepo.findByOauth2Id(id)
			.orElseThrow(() -> new UserNotFoundException(id)));
		
		return userMapper.toUserProfileResponse(user);
	}
	
	
	
}
