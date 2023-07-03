package dev.mrkevr.ecommerce.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import dev.mrkevr.ecommerce.dto.ContactDto;
import dev.mrkevr.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/contact")
public class ContactController {

	private final UserService userServ;

	@GetMapping
	ModelAndView contact() 
	{
		ModelAndView mav = new ModelAndView("contact");
		mav.addObject("title", "Contact - E-Commerce");
		mav.addObject("contactDto", new ContactDto());
		return mav;
	}
	
	@PostMapping("/process-contact")
	ModelAndView processContact(@ModelAttribute("contactDto") ContactDto contactDto) 
	{
		ModelAndView mav = new ModelAndView("contact");
		mav.addObject("contactDto", new ContactDto());
		return mav;
	}
}
