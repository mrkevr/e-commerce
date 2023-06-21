package dev.mrkevr.ecommerce.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import dev.mrkevr.ecommerce.dto.OrderResponse;
import dev.mrkevr.ecommerce.servioe.OrderService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin/orders")
@RequiredArgsConstructor
public class AdminOrderController {
	
	private final OrderService orderServ;
	
	@GetMapping
	ModelAndView orders() 
	{
		ModelAndView mav = new ModelAndView("admin/orders");
		mav.addObject("title", "Orders - Admin");
		mav.addObject("orders", orderServ.getAllActiveOrders());
		return mav;
	}
	
	@GetMapping("/{id}")
	ModelAndView orderDetails(@PathVariable String id) 
	{
		ModelAndView mav = new ModelAndView("admin/order-details");
		OrderResponse order = orderServ.getById(id);
		mav.addObject("order", order);
		mav.addObject("title", "Order#"+order.getId()+" - Admin");
		return mav;
	}
}
