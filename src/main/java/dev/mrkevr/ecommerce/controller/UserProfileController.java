package dev.mrkevr.ecommerce.controller;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import dev.mrkevr.ecommerce.dto.UserProfileResponse;
import dev.mrkevr.ecommerce.entity.User;
import dev.mrkevr.ecommerce.servioe.UserService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/profile")
@RequiredArgsConstructor
public class UserProfileController {

	private final UserService userServ;
	
	@ModelAttribute(name = "userProfileDto")
	UserProfileResponse userProfileDto() {
		
		SecurityContext securityContext = SecurityContextHolder.getContext();
		if (securityContext.getAuthentication().getPrincipal() instanceof DefaultOAuth2User) {
			
			DefaultOAuth2User principal = (DefaultOAuth2User) securityContext.getAuthentication().getPrincipal();
			String id = principal.getAttribute("sub");
			if (id == null) {
				id = principal.getAttribute("id");
			}
			
			return userServ.getProfileResponseById(id);
			
		} else {
			
			User user = (User) securityContext.getAuthentication().getPrincipal();
			return userServ.getProfileResponseById(user.getId());
		}
	}

	@GetMapping
	ModelAndView userProfile() {
		ModelAndView mav = new ModelAndView("user-profile");
		
		
		
		
		
		return mav;
	}

}
