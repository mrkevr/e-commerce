package dev.mrkevr.ecommerce.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import dev.mrkevr.ecommerce.servioe.CategoryService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin/categories")
@RequiredArgsConstructor
public class AdminCategoriesController {
	
	private final CategoryService categoryServ;
	
	@GetMapping
	ModelAndView categories() {

		ModelAndView mav = new ModelAndView("admin/categories");
		mav.addObject("categories", categoryServ.findAll());

		return mav;
	}

	@DeleteMapping("/delete-category/{id}")
	String processDeleteCategoryById(@PathVariable String id) {
		
		
		

		return "";
	}
	
	
}
