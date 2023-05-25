package dev.mrkevr.ecommerce.controller;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import dev.mrkevr.ecommerce.dto.LoggedInUserDetails;
import dev.mrkevr.ecommerce.entity.User;
import dev.mrkevr.ecommerce.servioe.ApplicationUserManager;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HomeController {

	private final ApplicationUserManager applicationUserManager;
	
	@ModelAttribute(name = "userDetails")
	LoggedInUserDetails loggedInUserDetails() {
		
		SecurityContext securityContext = SecurityContextHolder.getContext();
		
		if (securityContext.getAuthentication().getPrincipal() instanceof DefaultOAuth2User) {

			DefaultOAuth2User principal = (DefaultOAuth2User) securityContext.getAuthentication().getPrincipal();
			String login = principal.getAttribute("email");
			if (login == null) {
				login = principal.getAttribute("login");
			}

			User user = (User) applicationUserManager.loadUserByUsername(login);
			return new LoggedInUserDetails(user.getUsername(), user.getEmail());

		} else {

			User user = (User) securityContext.getAuthentication().getPrincipal();
			return new LoggedInUserDetails(user.getUsername(), user.getEmail());

		}
	}

	@GetMapping({ "/", "/dashboard" })
	ModelAndView displayDashboard() {
		ModelAndView mav = new ModelAndView("index");

		



//		SecurityContext securityContext = SecurityContextHolder.getContext();
//
//		if (securityContext.getAuthentication().getPrincipal() instanceof DefaultOAuth2User) {
//
//			DefaultOAuth2User principal = (DefaultOAuth2User) securityContext.getAuthentication().getPrincipal();
//			String login = principal.getAttribute("email");
//			if (login == null) {
//				login = principal.getAttribute("login");
//			}
//			User user = (User) applicationUserManager.loadUserByUsername(login);
//			mav.addObject("userDetails", new LoggedInUserDetails(user.getUsername(), user.getEmail()));
//
//		} else {
//
//			User user = (User) securityContext.getAuthentication().getPrincipal();
//
//			mav.addObject("userDetails", new LoggedInUserDetails(user.getUsername(), user.getEmail()));
//		}

		return mav;
	}
}
