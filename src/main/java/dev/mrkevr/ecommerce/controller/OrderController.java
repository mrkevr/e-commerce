package dev.mrkevr.ecommerce.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import dev.mrkevr.ecommerce.dto.LoggedInUserDetails;
import dev.mrkevr.ecommerce.service.OrderService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

	private final OrderService orderServ;
	
	@GetMapping
	ModelAndView orders(
		@ModelAttribute("userDetails") LoggedInUserDetails userDetails) 
	{
		ModelAndView mav = new ModelAndView("orders");
		mav.addObject("title", "Orders - E-Commerce");
		mav.addObject("orders", orderServ.getAllActiveByUserId(userDetails.getId()));
		return mav;
	}
	
	
}
