package dev.mrkevr.ecommerce.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import dev.mrkevr.ecommerce.dto.ProductRequest;
import dev.mrkevr.ecommerce.servioe.CategoryService;
import dev.mrkevr.ecommerce.servioe.ProductService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin/products")
@RequiredArgsConstructor
public class AdminProductController {
	
	private final ProductService productServ;
	private final CategoryService categoryServ;
	
	@GetMapping
	ModelAndView products() {

		ModelAndView mav = new ModelAndView("admin/products");
		mav.addObject("products", productServ.findAll());
		return mav;
	}
	
	@GetMapping("/new-product")
	ModelAndView newProduct() {
		
		ModelAndView mav = new ModelAndView("admin/new-product");
		mav.addObject("productRequest", new ProductRequest());
		mav.addObject("categories", categoryServ.findAllByActivatedTrue());
		
		return mav;
	}
}
