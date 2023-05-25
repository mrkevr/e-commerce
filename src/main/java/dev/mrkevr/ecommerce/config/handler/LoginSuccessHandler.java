package dev.mrkevr.ecommerce.config.handler;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import dev.mrkevr.ecommerce.entity.Role;
import dev.mrkevr.ecommerce.entity.User;
import dev.mrkevr.ecommerce.repository.RoleRepository;
import dev.mrkevr.ecommerce.repository.UserRepository;
import dev.mrkevr.ecommerce.servioe.ApplicationUserManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
	
	private final ApplicationUserManager userManager;
	private final UserRepository userRepo;
	private final RoleRepository roleRepo;
	private final PasswordEncoder passwordEncoder;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		String redirectUrl = null;
		if (authentication.getPrincipal() instanceof DefaultOAuth2User) {
			
			DefaultOAuth2User userDetails = (DefaultOAuth2User) authentication.getPrincipal();
			
			System.out.println(userDetails.getAttributes());
			
			
			String username = userDetails.getAttribute("email") != null ? userDetails.getAttribute("email")
					: userDetails.getAttribute("login") + "@gmail.com";
			
			if (userRepo.findByEmail(username).isEmpty()) {
				User user = new User();
				user.setEmail(username);
				user.setUsername(userDetails.getAttribute("email") != null ? userDetails.getAttribute("email")
						: userDetails.getAttribute("login"));
				user.setPassword(passwordEncoder.encode("PASSWORD"));
				Role role = roleRepo.findByRole("USER").get();
				user.getRoles().add(role);
				
				
				userManager.createUser(user);
			}
		}
		redirectUrl = "/dashboard";
		new DefaultRedirectStrategy().sendRedirect(request, response, redirectUrl);
	}
}
