package dev.mrkevr.ecommerce.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dev.mrkevr.ecommerce.dto.ContactMessageDto;
import dev.mrkevr.ecommerce.service.EmailService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/contact")
public class ContactController {
	
	EmailService emailService;
	
	@ModelAttribute(name = "title")
	String title() {
		return "Contact - E-Commerce";
	}

	@GetMapping
	ModelAndView contactEmailForm() {
		ModelAndView mav = new ModelAndView("contact");
		mav.addObject("messageDto", new ContactMessageDto());
		return mav;
	}
	
	@PostMapping
	String sendContactEmail(
			@Valid @ModelAttribute("messageDto") ContactMessageDto contactDto, 
			BindingResult result,
			Model model, 
			RedirectAttributes redirectAttrs) {

		if (result.hasErrors()) {
			model.addAttribute("error", "Message is not sent.");
			return "contact";
		}
		emailService.sendContactMessage(contactDto);
		redirectAttrs.addFlashAttribute("success", "Message sent. Thank you for contacting us.");
		return "redirect:/contact";
	}
}
