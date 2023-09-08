package dev.mrkevr.ecommerce.controller.admin;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import dev.mrkevr.ecommerce.dto.OrderResponse;
import dev.mrkevr.ecommerce.dto.ShoppingCartResponse;
import dev.mrkevr.ecommerce.dto.UserProfileResponse;
import dev.mrkevr.ecommerce.service.OrderService;
import dev.mrkevr.ecommerce.service.ShoppingCartService;
import dev.mrkevr.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin/users")
@RequiredArgsConstructor
public class AdminUserController {

	private final UserService userServ;
	private final ShoppingCartService shoppingCartServ;
	private final OrderService orderServ;
	
	@GetMapping
	ModelAndView users() {
		ModelAndView mav = new ModelAndView("admin/users");
		mav.addObject("title", "Users - Admin");
		mav.addObject("users", userServ.getAllUsersDto());
		return mav;
	}

	@GetMapping("/{id}")
	ModelAndView userDetails(@PathVariable String id) {
		ModelAndView mav = new ModelAndView("admin/user-details");
		UserProfileResponse user = userServ.getProfileDto(id);
		ShoppingCartResponse shoppingCart = shoppingCartServ.getByUserId(id);
		List<OrderResponse> orders = orderServ.getAllByUserId(id);
		
		mav.addObject("title", user.getUsername()+" - Admin");
		mav.addObject("user", user);
		mav.addObject("shoppingCart", shoppingCart);
		mav.addObject("orders", orders);
		return mav;
	}
}
