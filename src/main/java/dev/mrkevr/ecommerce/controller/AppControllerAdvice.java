package dev.mrkevr.ecommerce.controller;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import dev.mrkevr.ecommerce.dto.LoggedInUserDetails;
import dev.mrkevr.ecommerce.entity.User;
import dev.mrkevr.ecommerce.servioe.ApplicationUserManagerImpl;
import lombok.RequiredArgsConstructor;

@ControllerAdvice
@RequiredArgsConstructor
public class AppControllerAdvice {

	private final ApplicationUserManagerImpl applicationUserManager;

//	@ModelAttribute("userDetails")
//	LoggedInUserDetails loggedInUserDetails(SecurityContext securityContext) {
//
//		if (securityContext.getAuthentication().getPrincipal() instanceof DefaultOAuth2User) {
//
//			DefaultOAuth2User principal = (DefaultOAuth2User) securityContext.getAuthentication().getPrincipal();
//			String email = principal.getAttribute("email");
//			User user = (User) applicationUserManager.loadUserByUsername(email);
//			return new LoggedInUserDetails(user.getUsername(), user.getEmail());
//			
//		} else {
//
//			User user = (User) securityContext.getAuthentication().getPrincipal();
//			return new LoggedInUserDetails(user.getUsername(), user.getEmail());
//			
//		}
//	}

}
