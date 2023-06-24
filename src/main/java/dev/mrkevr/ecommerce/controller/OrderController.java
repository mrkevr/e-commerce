package dev.mrkevr.ecommerce.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import dev.mrkevr.ecommerce.dto.OrderRequest;
import dev.mrkevr.ecommerce.dto.OrderResponse;
import dev.mrkevr.ecommerce.service.OrderService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

	private final OrderService orderServ;

	@GetMapping("/preview")
	public OrderRequest previewOrderRequest() {

		return orderServ.previewOrderRequest();
	}
	
	
	@RequestMapping(value = "/check-out", method = { RequestMethod.GET, RequestMethod.POST })
	public OrderResponse checkOutShoppingCart() {
		
		OrderRequest orderRequest = OrderRequest.builder()
			.paymentMethod("COD")
			.deliveryAddress("Winterfell, Westeros")
			.message("Pls handle with care :D")
			.build();
		
		return orderServ.addOrder(orderRequest);
	}
}
