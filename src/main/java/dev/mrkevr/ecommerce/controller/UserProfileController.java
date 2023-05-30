package dev.mrkevr.ecommerce.controller;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dev.mrkevr.ecommerce.dto.UserProfileDto;
import dev.mrkevr.ecommerce.entity.User;
import dev.mrkevr.ecommerce.mapper.UserMapper;
import dev.mrkevr.ecommerce.servioe.ApplicationUserManager;
import dev.mrkevr.ecommerce.servioe.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/profile")
public class UserProfileController {

	private final UserService userServ;
	private final ApplicationUserManager userManager;
	private final UserMapper userMapper;

	@ModelAttribute("userProfileDto")
	private UserProfileDto userProfileDto() {

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
	String userProfile() {

		System.out.println("AT USER PROFILE GET CONTROLLER");

		return "user-profile";
	}

	@PostMapping
	String userProfile(@ModelAttribute("userProfileDto") @Valid UserProfileDto userProfileDto, BindingResult result,
			RedirectAttributes redirectAttrs) {

		if (result.hasErrors()) {
			return "user-profile";
		}

		System.out.println(userProfileDto.toString());

		userManager.updateUser(userProfileDto);

		redirectAttrs.addFlashAttribute("useruUpdateSuccessful", "Profile is updated.");
		redirectAttrs.addFlashAttribute("userProfileDto", userProfileDto);
		return "redirect:profile";
	}

//	private void resetAuthentication() {
//		
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//
//		Authentication newAuth = new UsernamePasswordAuthenticationToken(auth.getPrincipal(), auth.getCredentials(), updatedAuthorities);
//
//		SecurityContextHolder.getContext().setAuthentication(newAuth);
//	}

}
