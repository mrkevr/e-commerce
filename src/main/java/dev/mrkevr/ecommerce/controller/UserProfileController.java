package dev.mrkevr.ecommerce.controller;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dev.mrkevr.ecommerce.dto.UserProfileResponse;
import dev.mrkevr.ecommerce.entity.User;
import dev.mrkevr.ecommerce.mapper.UserMapper;
import dev.mrkevr.ecommerce.service.ApplicationUserManager;
import dev.mrkevr.ecommerce.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/profile")
public class UserProfileController {

	private final UserService userServ;
	private final ApplicationUserManager userManager;
	private final UserMapper userMapper;

//	@ModelAttribute("userProfileDto")
	private UserProfileResponse userProfileDto() {

		System.out.println("AT MODEL PROFILE");

		SecurityContext securityContext = SecurityContextHolder.getContext();
		if (securityContext.getAuthentication().getPrincipal() instanceof DefaultOAuth2User) {

			DefaultOAuth2User principal = (DefaultOAuth2User) securityContext.getAuthentication().getPrincipal();
			String id = principal.getAttribute("sub");

			if (id == null) {
				id = principal.getAttribute("id").toString();
			}

			System.out.println("READING OAUTH2 PROFILE");
			return userServ.getProfileDto(id);

		} else {

			User user = (User) securityContext.getAuthentication().getPrincipal();
			System.out.println("READING APP USER PROFILE");
			return userMapper.toUserProfileResponse(user);
		}
	}

	@GetMapping
	ModelAndView userProfile() {
		ModelAndView mav = new ModelAndView("user-profile");
		mav.addObject("userProfileDto", this.userProfileDto());
		return mav;
	}

	@PostMapping
	String userProfile(@ModelAttribute("userProfileDto") @Valid UserProfileResponse userProfileDto, BindingResult result,
			RedirectAttributes redirectAttrs) {

		if (result.hasErrors()) {
			return "user-profile";
		}

		System.out.println(userProfileDto.toString());

		userManager.updateUser(userProfileDto);

		redirectAttrs.addFlashAttribute("success", "Profile is updated.");
//		redirectAttrs.addFlashAttribute("userProfileDto", userProfileDto);
		return "redirect:profile";
	}
}
