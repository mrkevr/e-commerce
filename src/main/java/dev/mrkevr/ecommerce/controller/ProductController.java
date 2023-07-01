package dev.mrkevr.ecommerce.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import dev.mrkevr.ecommerce.dto.CategoryResponse;
import dev.mrkevr.ecommerce.dto.ProductResponse;
import dev.mrkevr.ecommerce.service.CategoryService;
import dev.mrkevr.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
	
	private final ProductService productServ;
	private final CategoryService categoryServ;

	@GetMapping("/categories")
	ModelAndView categories() 
	{
		ModelAndView mav = new ModelAndView("categories");
		mav.addObject("title", "Shop - E-Commerce");
		
		List<CategoryResponse> availableCategories = categoryServ.getAvailableCategories();
		
		mav.addObject("categories", availableCategories);
		
		return mav;
	}
	
	@GetMapping
	ModelAndView products(
			@RequestParam(required = false, name = "category") String category,
			@RequestParam(required = false, name = "search") String search)
	{
		if(search != null) {
			ModelAndView mav = new ModelAndView("products");
			mav.addObject("title", search + " - E-Commerce");
			mav.addObject("products", productServ.searchProducts(search));
			return mav;
		}
		
		ModelAndView mav = new ModelAndView("products");
		mav.addObject("title", category + " - E-Commerce");
		mav.addObject("category", category);
		mav.addObject("products", productServ.getAllActivatedByCategory(category));
		return mav;
	}
		
	@GetMapping("/{id}")
	ModelAndView productDetails(@PathVariable String id) 
	{
		ModelAndView mav = new ModelAndView("product");
		ProductResponse product = productServ.getById(id);
		mav.addObject("product", product);
		mav.addObject("title", product.getName()+" - E-Commerce");
		mav.addObject("relatedProducts", productServ.getRandomProducts(4));
		return mav;
	}
	
	
	
	

}
