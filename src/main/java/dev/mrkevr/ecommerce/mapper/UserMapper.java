package dev.mrkevr.ecommerce.mapper;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

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
}
