package dev.mrkevr.ecommerce.service.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.mrkevr.ecommerce.dto.UserProfileResponse;
import dev.mrkevr.ecommerce.dto.UserRegistrationRequest;
import dev.mrkevr.ecommerce.entity.Role;
import dev.mrkevr.ecommerce.entity.ShoppingCart;
import dev.mrkevr.ecommerce.entity.User;
import dev.mrkevr.ecommerce.exception.RoleNotFoundException;
import dev.mrkevr.ecommerce.exception.UserNotFoundException;
import dev.mrkevr.ecommerce.mapper.UserMapper;
import dev.mrkevr.ecommerce.repository.RoleRepository;
import dev.mrkevr.ecommerce.repository.ShoppingCartRepository;
import dev.mrkevr.ecommerce.repository.UserRepository;
import dev.mrkevr.ecommerce.service.ApplicationUserManager;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ApplicationUserManagerImpl implements ApplicationUserManager {

	private final UserRepository userRepo;
	private final RoleRepository roleRepo;
	private final ShoppingCartRepository shoppingCartRepo;
	private final UserMapper userMapper;

	@Override
	public void changePassword(String oldPassword, String newPassword) {
		// TODO Auto-generated method stub
	}

	@Override
	@Transactional
	public void createUser(UserDetails user) {
		User newUser = (User) user;
		userRepo.save(newUser);
	}

	@Override
	@Transactional
	public void deleteUser(String username) {
		// TODO Auto-generated method stub
	}

	@Override
	@Transactional
	public void updateUser(UserDetails user) {
		User toUpdate = (User) user;
		userRepo.save(toUpdate);
	}

	@Override
	public boolean userExists(String username) {
		return userRepo.existsByUsername(username);
	}

	@Override
	public UserDetails loadUserByUsername(String identity) {
		
		if (userRepo.findByEmail(identity).isPresent()) {
			return userRepo.findByEmail(identity).get();
		}
		if (userRepo.findByUsername(identity).isPresent()) {
			return userRepo.findByUsername(identity).get();
		}
		if (userRepo.findByOauth2Id(identity).isPresent()) {
			return userRepo.findByOauth2Id(identity).get();
		}
		throw new UserNotFoundException();
	}

	@Override
	public boolean emailAlreadyTaken(String email) {
		return userRepo.existsByEmail(email);
	}

	@Override
	public boolean usernameAlreadyTaken(String username) {
		return userRepo.existsByUsername(username);
	}

	@Override
	@Transactional
	public void deleteUserById(String id) {
		User toDelete = userRepo.findById(id).orElseThrow();
		userRepo.delete(toDelete);
	}

	@Override
	@Transactional
	public UserProfileResponse registerUser(UserRegistrationRequest userRegistrationRequest) {
		Role role = roleRepo.findByRoleIgnoreCase("ROLE_USER").orElseThrow(() -> new RoleNotFoundException());
		ShoppingCart shoppingCart = new ShoppingCart();
		User user = userMapper.toUser(userRegistrationRequest, role, shoppingCart);
		shoppingCart.setUser(user);
		
		User savedUser = userRepo.save(user);
		
		
		
		
		
		return userMapper.toUserProfileResponse(savedUser);
	}
	
	@Override
	@Transactional
	public User registerUser(User user) {
		Role role = roleRepo.findByRoleIgnoreCase("ROLE_USER").orElseThrow(() -> new RoleNotFoundException());
		ShoppingCart shoppingCart = new ShoppingCart();
		shoppingCart.setUser(user);
		user.setShoppingCart(shoppingCart);
		user.setRoles(new HashSet<>(Arrays.asList(role)));
		
		
		return userRepo.save(user);
	}
	

	@Override
	@Transactional
	public UserProfileResponse updateUser(UserProfileResponse dto) {
		User userToUpdate = userRepo.findById(dto.getId()).orElseThrow(() -> new UserNotFoundException(dto.getId()));
		userToUpdate.setFirstName(dto.getFirstName());
		userToUpdate.setLastName(dto.getLastName());
		userToUpdate.setAddress(dto.getAddress());
		userToUpdate.setPhone(dto.getPhone());
		User savedUser = userRepo.save(userToUpdate);
		return userMapper.toUserProfileResponse(savedUser);
	}
	
	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles) {
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getRole())).collect(Collectors.toList());
	}

	
}
