package dev.mrkevr.ecommerce.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;

import dev.mrkevr.ecommerce.servioe.impl.ApplicationUserManagerImpl;
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
