package dev.mrkevr.ecommerce.config.handler;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import dev.mrkevr.ecommerce.constant.RoleConstant;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import dev.mrkevr.ecommerce.entity.User;
import dev.mrkevr.ecommerce.repository.UserRepository;
import dev.mrkevr.ecommerce.service.ApplicationUserManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import static dev.mrkevr.ecommerce.constant.RoleConstant.ROLE_ADMIN;

@Component
@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

	private final ApplicationUserManager userManager;
	private final UserRepository userRepo;
	private final PasswordEncoder passwordEncoder;

	@Override
	public void onAuthenticationSuccess(
			HttpServletRequest request, 
			HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		String redirectUrl = null;
		if (authentication.getPrincipal() instanceof DefaultOAuth2User) {
			
			DefaultOAuth2User userDetails = (DefaultOAuth2User) authentication.getPrincipal();
			// Getting the id (sub for google and id for github)
			String oauth2Id = userDetails.getAttribute("sub") != null ? 
					userDetails.getAttribute("sub").toString() : userDetails.getAttribute("id").toString();
			
			if (userRepo.findByOauth2Id(oauth2Id).isEmpty()) {
				registerOauth2User(oauth2Id, userDetails);
			}
		}
		
		Set<String> roles = authentication.getAuthorities()
				.stream()
				.map(r -> r.getAuthority())
				.collect(Collectors.toSet());

		if (roles.contains(ROLE_ADMIN)) {
			redirectUrl = "/admin/dashboard";
		} else {
			redirectUrl = "/dashboard";
		}
		
		new DefaultRedirectStrategy().sendRedirect(request, response, redirectUrl);
	}

	private void registerOauth2User(String oauth2Id, DefaultOAuth2User userDetails) {
		
		User user = new User();
		user.setOauth2Id(oauth2Id);
		user.setUsername(userDetails.getAttribute("email") != null ? 
				userDetails.getAttribute("email") : userDetails.getAttribute("login"));
		user.setEmail(userDetails.getAttribute("email") != null ? 
				userDetails.getAttribute("email") : null);
		user.setPassword(passwordEncoder.encode(UUID.randomUUID().toString()));
		
		
		User registerUser = userManager.registerUser(user);
	}
}
