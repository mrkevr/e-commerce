package dev.mrkevr.ecommerce.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

//	@PostMapping("/enable")
	@RequestMapping(value = "/enable", method = { RequestMethod.GET, RequestMethod.POST })
	String enableCategoryById(@RequestParam String id, RedirectAttributes redirectAttrs) {
		String name = categoryServ.enableById(id).getName();
		redirectAttrs.addFlashAttribute("categoryUpdateSuccessful", name + " has been enabled.");
		return "redirect:/admin/categories";
	}

//	@PostMapping("/disable")
	@RequestMapping(value = "/disable", method = { RequestMethod.GET, RequestMethod.POST })
	String disableCategoryById(@RequestParam String id, RedirectAttributes redirectAttrs) {
		String name = categoryServ.disableById(id).getName();
		redirectAttrs.addFlashAttribute("categoryUpdateSuccessful", name + " has been disabled.");
		return "redirect:/admin/categories";
	}

}
