package dev.mrkevr.ecommerce.controller.advice;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import dev.mrkevr.ecommerce.dto.LoggedInUserDetails;
import dev.mrkevr.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;

@ControllerAdvice
@RequiredArgsConstructor
public class ApplicationControllerAdvice {

	private final UserService userServ;

	@ModelAttribute(name = "userDetails")
	LoggedInUserDetails loggedInUserDetails(Authentication authentication) {
		return userServ.getLoggedInUserDetails(authentication);
	}
}
