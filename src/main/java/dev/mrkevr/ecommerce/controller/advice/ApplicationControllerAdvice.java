package dev.mrkevr.ecommerce.controller.advice;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import dev.mrkevr.ecommerce.dto.LoggedInUserDetails;
import dev.mrkevr.ecommerce.entity.User;
import dev.mrkevr.ecommerce.servioe.ApplicationUserManager;
import lombok.RequiredArgsConstructor;

@ControllerAdvice
@RequiredArgsConstructor
public class ApplicationControllerAdvice {

	private final ApplicationUserManager applicationUserManager;

	@ModelAttribute(name = "userDetails")
	LoggedInUserDetails loggedInUserDetails(Authentication authentication) {
		
		if(authentication instanceof AnonymousAuthenticationToken || authentication == null) {
			return new LoggedInUserDetails("visitor", "");
		}
		
		Object principal = authentication.getPrincipal();
		if (principal instanceof DefaultOAuth2User) {
			
			DefaultOAuth2User oauth2user = (DefaultOAuth2User) principal;
			String loginId = oauth2user.getAttribute("email");
			if (loginId == null) {
				loginId = oauth2user.getAttribute("login");
			}
			User user = (User) applicationUserManager.loadUserByUsername(loginId);
			return new LoggedInUserDetails(user.getUsername(), user.getEmail());
			
		} else {
			User user = (User) principal;
			return new LoggedInUserDetails(user.getUsername(), user.getEmail());
		}
	}
	
//	@ModelAttribute(name = "userDetails")
//	LoggedInUserDetails loggedInUserDetails() {
//		
//		SecurityContext securityContext = SecurityContextHolder.getContext();
//		
//		if(securityContext.getAuthentication() instanceof AnonymousAuthenticationToken) {
//			return new LoggedInUserDetails("visitor", "");
//		}
//
//		
//		
//		
//		if (securityContext.getAuthentication().getPrincipal() instanceof DefaultOAuth2User) {
//			
//			DefaultOAuth2User principal = (DefaultOAuth2User) securityContext.getAuthentication().getPrincipal();
//			String login = principal.getAttribute("email");
//			if (login == null) {
//				login = principal.getAttribute("login");
//			}
//			
//			User user = (User) applicationUserManager.loadUserByUsername(login);
//			return new LoggedInUserDetails(user.getUsername(), user.getEmail());
//			
//		} else {
//			
//			User user = (User) securityContext.getAuthentication().getPrincipal();
//			return new LoggedInUserDetails(user.getUsername(), user.getEmail());
//		}
//	}
}
