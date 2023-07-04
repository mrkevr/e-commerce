package dev.mrkevr.ecommerce.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.mrkevr.ecommerce.dto.LoggedInUserDetails;
import dev.mrkevr.ecommerce.dto.UserProfileResponse;
import dev.mrkevr.ecommerce.entity.User;
import dev.mrkevr.ecommerce.exception.UserNotFoundException;
import dev.mrkevr.ecommerce.mapper.UserMapper;
import dev.mrkevr.ecommerce.repository.UserRepository;
import dev.mrkevr.ecommerce.service.ApplicationUserManager;
import dev.mrkevr.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepo;
	private final UserMapper userMapper;
	private final ApplicationUserManager userManager;

	@Override
	public UserProfileResponse getProfileDto(String id) {
		User user = userRepo.findById(id)
			.orElseGet(() -> userRepo.findByOauth2Id(id)
			.orElseThrow(() -> new UserNotFoundException(id)));

		return userMapper.toUserProfileResponse(user);
	}

	@Override
	public List<UserProfileResponse> getAllUsersDto() {
		List<User> list = userRepo.findAll().stream()
				.filter(user -> !user.getUsername().equals("admin"))
				.collect(Collectors.toList());

		return userMapper.toUserProfileResponse(list);
	}

	@Override
	public User getCurrentUser() {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		if (securityContext.getAuthentication().getPrincipal() instanceof DefaultOAuth2User) {
			DefaultOAuth2User principal = (DefaultOAuth2User) securityContext.getAuthentication().getPrincipal();
			String id = principal.getAttribute("sub");

			if (id == null) {
				id = principal.getAttribute("id").toString();
			}
			UserDetails userDetails = userManager.loadUserByUsername(id);
			return (User) userDetails;
			
		} else {
			return (User) securityContext.getAuthentication().getPrincipal();
		}
	}
	
	@Override
	public UserProfileResponse getCurrentUserProfileResponse() {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		if (securityContext.getAuthentication().getPrincipal() instanceof DefaultOAuth2User) {
			DefaultOAuth2User principal = (DefaultOAuth2User) securityContext.getAuthentication().getPrincipal();
			String id = principal.getAttribute("sub");

			if (id == null) {
				id = principal.getAttribute("id").toString();
			}
			UserDetails userDetails = userManager.loadUserByUsername(id);
			return userMapper.toUserProfileResponse((User) userDetails);
			
		} else {
			return userMapper.toUserProfileResponse((User) securityContext.getAuthentication().getPrincipal());
		}
	}

	@Override
	public LoggedInUserDetails getLoggedInUserDetailsByUserId(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LoggedInUserDetails getLoggedInUserDetailsByUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}
}
