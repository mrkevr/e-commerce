package dev.mrkevr.ecommerce.controller.admin;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dev.mrkevr.ecommerce.converter.LocalDateConverter;
import dev.mrkevr.ecommerce.dto.OrderResponse;
import dev.mrkevr.ecommerce.dto.OrderUpdateRequest;
import dev.mrkevr.ecommerce.entity.OrderStatus;
import dev.mrkevr.ecommerce.service.OrderService;
import lombok.RequiredArgsConstructor;

import static dev.mrkevr.ecommerce.constant.ModelAttributeConstant.*;
import static dev.mrkevr.ecommerce.entity.OrderStatus.*;

@Controller
@RequestMapping("/admin/orders")
@RequiredArgsConstructor
public class AdminOrderController {
	
	private final OrderService orderServ;
	private final LocalDateConverter localDateConverter;
	
	@GetMapping
	ModelAndView orders(
			@RequestParam(required = true, defaultValue = "PENDING", name = "orderStatus") OrderStatus orderStatus) {

		List<OrderResponse> orders = orderServ.getAllByOrderStatus(orderStatus);
		ModelAndView mav = new ModelAndView("admin/orders");
		mav.addObject(TITLE, "Orders - Admin");
		mav.addObject(ORDER_STATUSES, OrderStatus.activeStatuses());
		mav.addObject(ORDERS, orders);
		mav.addObject(ORDER_STATUS, orderStatus);
		return mav;
	}
	
	@GetMapping("/{id}")
	ModelAndView orderDetails(@PathVariable String id) {
		
		ModelAndView mav = new ModelAndView("admin/order-details");
		OrderResponse order = orderServ.getById(id);
		List<OrderStatus> orderStatuses = List.of(
			IN_PROGRESS,
			TO_SHIP,
			TO_RECEIVE,
			COMPLETED,
			RETURNED);
		
		mav.addObject(ORDER, order);
		mav.addObject(ORDER_STATUSES, orderStatuses);
		mav.addObject(ORDER_UPDATE_REQUEST,
			new OrderUpdateRequest(
				id, 
				order.getOrderStatus(), 
				order.getDeliveryDate() == null ? localDateConverter.convert(LocalDate.now()) : localDateConverter.convert(order.getDeliveryDate()),
				order.getMessage()));

		mav.addObject(TITLE, "Order#"+order.getId()+" - Admin");
		return mav;
	}
	
	@RequestMapping(value = "/accept", method = { RequestMethod.GET, RequestMethod.POST })
	String acceptOrder(
			@RequestParam(name = "id", required = true)
			String id,
			RedirectAttributes redirectAttrs) {
		
		orderServ.acceptOrderById(id);
		redirectAttrs.addFlashAttribute(SUCCESS, "Order has been accepted.");
		return "redirect:/admin/orders/"+id;
	}
	
	@RequestMapping(value = "/deny", method = { RequestMethod.GET, RequestMethod.POST })
	String denyOrder(
			@RequestParam(name = "id", required = true) String id,
			RedirectAttributes redirectAttrs) {
		
		orderServ.denyOrderById(id);
		redirectAttrs.addFlashAttribute(WARNING, "Order has been denied.");
		return "redirect:/admin/orders/"+id;
	}
	
	@RequestMapping(value = "/update-order", method = { RequestMethod.GET, RequestMethod.POST })
	String updateOrder(
		@ModelAttribute(name = "orderUpdateRequest")
		OrderUpdateRequest orderUpdateRequest, 
		RedirectAttributes redirectAttrs) {
		
		orderServ.updateOrderById(orderUpdateRequest);
		redirectAttrs.addFlashAttribute(SUCCESS, "Order has been updated.");
		return "redirect:/admin/orders/"+orderUpdateRequest.getId();
	}
	
}
