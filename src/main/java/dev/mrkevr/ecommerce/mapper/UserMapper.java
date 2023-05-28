package dev.mrkevr.ecommerce.mapper;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import dev.mrkevr.ecommerce.dto.UserProfileResponse;
import dev.mrkevr.ecommerce.dto.UserRegistrationRequest;
import dev.mrkevr.ecommerce.entity.Role;
import dev.mrkevr.ecommerce.entity.User;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserMapper {

	private final PasswordEncoder passwordEncoder;

	public User toUser(UserRegistrationRequest dto, Role role) {
		User user = new User();
		user.setUsername(dto.getUsername());
		user.setFirstName(dto.getFirstName());
		user.setLastName(dto.getLastName());
		user.setAddress(dto.getAddress());
		user.setEmail(dto.getEmail());
		user.setPhone(dto.getPhone());
		user.setPassword(passwordEncoder.encode(dto.getPassword()));
		user.getRoles().add(role);
		return user;
	}

	public UserProfileResponse toUserProfileResponse(User user) {
		UserProfileResponse response = new UserProfileResponse();
		response.setId(user.getId());
		response.setOauth2Id(user.getOauth2Id());
		response.setUsername(user.getUsername());
		response.setFirstName(user.getFirstName());
		response.setLastName(user.getLastName());
		response.setAddress(
				user.getAddress().getStreet() + ", " + 
		user.getAddress().getBarangay() + ", "
				+ user.getAddress().getMunicipality() + ", " + user.getAddress().getProvince() + " "
				+ user.getAddress().getZipcode());
		response.setEmail(user.getEmail());
		response.setPhone(user.getPhone());
		return response;
	}
}
