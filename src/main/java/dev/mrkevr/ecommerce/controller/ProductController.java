package dev.mrkevr.ecommerce.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import dev.mrkevr.ecommerce.dto.ProductResponse;
import dev.mrkevr.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
	
	private final ProductService productServ;
	
	@GetMapping("/{id}")
	ModelAndView productDetails(@PathVariable String id) 
	{
		ModelAndView mav = new ModelAndView("product");
		ProductResponse product = productServ.getById(id);
		mav.addObject("product", product);
		mav.addObject("title", product.getName()+" - E-Commerce");
		return mav;
	}

}
