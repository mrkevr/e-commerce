package dev.mrkevr.ecommerce.servioe.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import dev.mrkevr.ecommerce.dto.UserProfileDto;
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
	public UserProfileDto getProfileDto(String id) {
		User user = userRepo.findById(id)
			.orElseGet(() -> userRepo.findByOauth2Id(id)
			.orElseThrow(() -> new UserNotFoundException(id)));
		
		return userMapper.toUserProfileResponse(user);
	}

	@Override
	public List<UserProfileDto> getAllUsersDto() {
		List<User> list = userRepo.findAll().stream().filter(user -> !user.getUsername().equals("admin")).collect(Collectors.toList());
		
		
		return userMapper.toUserProfileResponse(list);
	}
	
	
	
}
