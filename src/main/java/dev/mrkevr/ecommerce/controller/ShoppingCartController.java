package dev.mrkevr.ecommerce.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cart")
public class ShoppingCartController {
	
	
	
	@GetMapping
	ModelAndView userProfile() 
	{
		ModelAndView mav = new ModelAndView("cart");
		mav.addObject("title", "Cart - E-Commerce");
		return mav;
	}
}
