package dev.mrkevr.ecommerce.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import dev.mrkevr.ecommerce.dto.ProductResponse;
import dev.mrkevr.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;

@RequestMapping("/search")
@Controller
@RequiredArgsConstructor
public class SearchController {

	private final ProductService productServ;

	@GetMapping
	ModelAndView search(@RequestParam(required = true, name = "search") String search) {
		ModelAndView mav = new ModelAndView("search");
		mav.addObject("title", search + " - E-Commerce");
		mav.addObject("search", search);

		if (search.length() < 2) {
			mav.addObject("noResult", true);
			mav.addObject("products", productServ.getRandomProducts(4));
			return mav;
		}

		List<ProductResponse> products = productServ.searchProducts(search);
		if (products.isEmpty()) {
			products = productServ.getRandomProducts(4);
			mav.addObject("noResult", true);
		}

		mav.addObject("products", products);
		return mav;
	}
}
