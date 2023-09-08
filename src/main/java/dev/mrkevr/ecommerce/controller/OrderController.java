package dev.mrkevr.ecommerce.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dev.mrkevr.ecommerce.dto.LoggedInUserDetails;
import dev.mrkevr.ecommerce.dto.OrderResponse;
import dev.mrkevr.ecommerce.service.OrderService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

	private final OrderService orderServ;
	
	@GetMapping
	ModelAndView orders(@ModelAttribute("userDetails") LoggedInUserDetails userDetails) {
		ModelAndView mav = new ModelAndView("orders");
		mav.addObject("title", "Orders - E-Commerce");
		mav.addObject("orders", orderServ.getAllActiveByUserId(userDetails.getId()));
		return mav;
	}
	
	@GetMapping("/{orderId}")
	ModelAndView orderById(
			@ModelAttribute("userDetails") LoggedInUserDetails userDetails,
			@PathVariable(name = "orderId", required = true) String orderId) {
		
		ModelAndView mav = new ModelAndView("order");
		OrderResponse order = orderServ.getByUserIdAndOrderId(userDetails.getId(), orderId);
		mav.addObject("title", "Order- E-Commerce");
		mav.addObject("order", order);
		return mav;
	}
	
	@RequestMapping(value = "/cancel", method = { RequestMethod.GET ,RequestMethod.PUT})
	String cancelOrder(
			@ModelAttribute("userDetails") LoggedInUserDetails userDetails,
			@RequestParam(name = "orderId", required = true) String orderId,
			RedirectAttributes redirectAttrs) {
		
		orderServ.cancelOrderById(userDetails.getId(), orderId);
		redirectAttrs.addFlashAttribute("warning", "Order has been cancelled.");
		return "redirect:/orders";
	}
}
