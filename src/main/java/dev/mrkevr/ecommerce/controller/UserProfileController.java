package dev.mrkevr.ecommerce.controller;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import dev.mrkevr.ecommerce.dto.UserProfileResponse;
import dev.mrkevr.ecommerce.entity.User;
import dev.mrkevr.ecommerce.mapper.UserMapper;
import dev.mrkevr.ecommerce.servioe.UserService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UserProfileController {

	private final UserService userServ;
	private final UserMapper userMapper;
	
	@ModelAttribute(name = "userProfileDto")
	UserProfileResponse userProfileDto() {
		
		System.out.println("AT MODEL PROFILE");
		
		
		SecurityContext securityContext = SecurityContextHolder.getContext();
		if (securityContext.getAuthentication().getPrincipal() instanceof DefaultOAuth2User) {
			
			DefaultOAuth2User principal = (DefaultOAuth2User) securityContext.getAuthentication().getPrincipal();
			String id = principal.getAttribute("sub");
			if (id == null) {
				id = principal.getAttribute("id");
			}
			
			System.out.println("READING OAUTH2 PROFILE");
			return userServ.getProfileResponseById(id);
			
		} else {
			
			User user = (User) securityContext.getAuthentication().getPrincipal();
			System.out.println("READING APP USER PROFILE");
			return userMapper.toUserProfileResponse(user);
		}
	}

	@GetMapping("/profile")
	ModelAndView userProfile() {
		ModelAndView mav = new ModelAndView("user-profile");
		
		
		System.out.println("AT USER PROFILE CONTROLLER");
		
		
		return mav;
	}

}
