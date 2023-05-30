package dev.mrkevr.ecommerce.controller;

import java.util.Set;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dev.mrkevr.ecommerce.dto.UserRegistrationRequest;
import dev.mrkevr.ecommerce.dto.validation.PasswordsEqualConstraint;
import dev.mrkevr.ecommerce.servioe.ApplicationUserManager;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/register")
@RequiredArgsConstructor
public class UserRegistrationController {
	
	private final ApplicationUserManager userManager;
	private final Validator validator;
	
	@GetMapping
	public ModelAndView showRegistrationForm() {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
			
			
			return new ModelAndView("redirect:dashboard");
		}
		
		ModelAndView mav = new ModelAndView("register");
		mav.addObject("registrationDto", new UserRegistrationRequest());
		return mav;
	}
	
	@PostMapping
	public String registerUserAccount(
			@ModelAttribute("registrationDto") 
			@Valid 
			UserRegistrationRequest registrationDto,
			BindingResult result,
			RedirectAttributes redirectAttrs) {
		
		// Username availability check
		if(userManager.usernameAlreadyTaken(registrationDto.getUsername())) {
			result.addError(new FieldError("registrationDto", "username", registrationDto.getUsername()+" is already taken."));
		}
		// Email availability check
		if(userManager.emailAlreadyTaken(registrationDto.getEmail())){
			result.addError(new FieldError("registrationDto", "email", "An account already exists for this email."));
		}
		// Password match check
		if(this.passwordsEqualConstraintViolated(registrationDto)){
			result.addError(new FieldError("registrationDto", "password", "Passwords did not match."));
		}
		if (result.hasErrors()) {
			return "register";
		}
		userManager.registerUser(registrationDto);
		redirectAttrs.addFlashAttribute("registrationSuccessful", "Registration successful!");
		return "redirect:login";
	}
	
	private boolean passwordsEqualConstraintViolated(UserRegistrationRequest registrationDto) {
		Set<ConstraintViolation<@Valid UserRegistrationRequest>> violations = validator.validate(registrationDto);
		return violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof PasswordsEqualConstraint);
	}
}
