package dev.mrkevr.ecommerce.mapper;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import dev.mrkevr.ecommerce.dto.UserProfileResponse;
import dev.mrkevr.ecommerce.dto.UserRegistrationRequest;
import dev.mrkevr.ecommerce.entity.Role;
import dev.mrkevr.ecommerce.entity.ShoppingCart;
import dev.mrkevr.ecommerce.entity.User;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserMapper {

	private final PasswordEncoder passwordEncoder;

	public User toUser(UserRegistrationRequest request, Role role, ShoppingCart shoppingCart) {
		
		User user =  User.builder()
			.username(request.getUsername())
			.firstName(request.getFirstName())
			.lastName(request.getLastName())
			.Address(request.getAddress())
			.email(request.getEmail())
			.phone(request.getPhone())
			.password(passwordEncoder.encode(request.getPassword()))
			.roles(new HashSet<>(Arrays.asList(role)))
			.shoppingCart(shoppingCart)
			.build();
		
		return user;
	}

	public UserProfileResponse toUserProfileResponse(User user) {
		return UserProfileResponse.builder()
			.id(user.getId())
			.username(user.getUsername())
			.firstName(user.getFirstName())
			.lastName(user.getLastName())
			.address(user.getAddress())
			.email(user.getEmail())
			.phone(user.getPhone())
			.created(user.getCreated())
			.modified(user.getModified())
			.totalCartItems(user.getShoppingCart().getCartItems().size())
			.totalActiveOrders(user.getOrders().size())
			.build();
	}

	public List<UserProfileResponse> toUserProfileResponse(List<User> users) {
		return users.stream().map(user -> this.toUserProfileResponse(user)).collect(Collectors.toList());
	}
}
