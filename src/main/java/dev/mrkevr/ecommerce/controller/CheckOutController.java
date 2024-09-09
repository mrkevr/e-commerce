package dev.mrkevr.ecommerce.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dev.mrkevr.ecommerce.dto.LoggedInUserDetails;
import dev.mrkevr.ecommerce.dto.OrderRequest;
import dev.mrkevr.ecommerce.entity.ShoppingCart;
import dev.mrkevr.ecommerce.service.OrderService;
import dev.mrkevr.ecommerce.service.ShoppingCartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import static dev.mrkevr.ecommerce.constant.ModelAttributeConstant.*;

@RequiredArgsConstructor
@Controller
@RequestMapping("/checkout")
public class CheckOutController {
	
	private final OrderService orderServ;
	private final ShoppingCartService shoppingCartServ;
	
	@GetMapping
	ModelAndView checkout(@ModelAttribute("userDetails") LoggedInUserDetails userDetails) {
		ModelAndView mav = new ModelAndView("checkout");
		mav.addObject(TITLE, "Checkout- E-Commerce");
		mav.addObject(SHOPPING_CART, shoppingCartServ.getByUserId(userDetails.getId()));
		mav.addObject(ORDER_REQUEST, new OrderRequest());
		return mav;
	}
	
	@PostMapping
	public String checkOutShoppingCart(
		@Valid
		@ModelAttribute(ORDER_REQUEST)
		OrderRequest orderRequest,
		BindingResult result,
		@ModelAttribute(SHOPPING_CART)
		ShoppingCart shoppingCart,
		RedirectAttributes redirectAttrs) {
		
		if(result.hasErrors()) {
			return "checkout";
		}

		orderServ.addOrder(orderRequest);
		redirectAttrs.addFlashAttribute(SUCCESS,"Thank you for your order and for supporting our small business! If you want to track your delivery, use the order tracker below.");
		return "redirect:/orders";
	}
}
