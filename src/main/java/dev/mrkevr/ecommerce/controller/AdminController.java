package dev.mrkevr.ecommerce.controller;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import dev.mrkevr.ecommerce.dto.LoggedInUserDetails;
import dev.mrkevr.ecommerce.entity.User;
import dev.mrkevr.ecommerce.servioe.ApplicationUserManager;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

	private final ApplicationUserManager applicationUserManager;

	@GetMapping
	ModelAndView dashboard() {
		
		SecurityContext securityContext = SecurityContextHolder.getContext();
		ModelAndView mav = new ModelAndView("admin-dashboard");
		User user = (User) applicationUserManager.loadUserByUsername("admin");
		
		mav.addObject("userDetails", new LoggedInUserDetails(user.getUsername(), user.getEmail()));
		return mav;
	}

}
