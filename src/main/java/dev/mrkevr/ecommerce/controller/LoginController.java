package dev.mrkevr.ecommerce.controller;

import java.util.Collection;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import dev.mrkevr.ecommerce.dto.UserLoginRequest;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

	@GetMapping
	public String login(@ModelAttribute UserLoginRequest userLoginRequest, Model model) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
			Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
			boolean isAdmin = authorities.stream().anyMatch(a -> a.toString().equals("ROLE_ADMIN"));
			return isAdmin ? "redirect:admin" : "redirect:dashboard";
		}
		userLoginRequest.setUsernameEmail("admin");
		model.addAttribute("title", "Login - E-Commerce");
		return "login";
	}
}