package dev.mrkevr.ecommerce.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dev.mrkevr.ecommerce.dto.LoggedInUserDetails;
import dev.mrkevr.ecommerce.service.ShoppingCartService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cart")
public class ShoppingCartController {
	
	private final ShoppingCartService shoppingCartServ;
	
	@GetMapping
	ModelAndView cart(
		@ModelAttribute("userDetails") LoggedInUserDetails userDetails) 
	{
		ModelAndView mav = new ModelAndView("cart");
		mav.addObject("title", "Cart - E-Commerce");
		mav.addObject("shoppingCart", shoppingCartServ.getByUserId(userDetails.getId()));
		return mav;
	}
	
	@PostMapping("/add")
	String addCartItem(
		Authentication authentication,
		RedirectAttributes redirectAttrs,
		@ModelAttribute("userDetails") LoggedInUserDetails userDetails, 
		@RequestParam(name = "productId", required = true) String productId, 
		@RequestParam(name = "quantity", required = false, defaultValue = "1") int quantity) 
	{
		shoppingCartServ.addCartItem(userDetails.getId(), productId, quantity);
		redirectAttrs.addFlashAttribute("success", "Item is added to cart.");
		return "redirect:/cart";
	}
	
	@RequestMapping(value = "/remove", method = { RequestMethod.GET ,RequestMethod.DELETE})
	String removeCartItem(
		Authentication authentication,
		RedirectAttributes redirectAttrs,
		@ModelAttribute("userDetails") LoggedInUserDetails userDetails, 
		@RequestParam(name = "cartId", required = true) String cartId) 
	{
		shoppingCartServ.deleteCartItem(userDetails.getId(), cartId);
		return "redirect:/cart"; 
	}
	
}
