package dev.mrkevr.ecommerce.controller.admin;

import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dev.mrkevr.ecommerce.dto.CategoryRequestDto;
import dev.mrkevr.ecommerce.dto.CategoryUpdateDto;
import dev.mrkevr.ecommerce.entity.Category;
import dev.mrkevr.ecommerce.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin/categories")
@RequiredArgsConstructor
public class AdminCategoriesController {

	private final CategoryService categoryServ;
	
	@ModelAttribute("title")
	String title() {
		return "Categories - Admin";
	}
	
	@GetMapping
	ModelAndView categories() {

		ModelAndView mav = new ModelAndView("admin/categories");
		mav.addObject("categories", categoryServ.findAll().stream().sorted((o1, o2) -> o1.getName().compareTo(o2.getName())).collect(Collectors.toList()));
		mav.addObject("categoryRequestDto", new CategoryRequestDto());
		mav.addObject("categoryUpdateDto", new CategoryUpdateDto());
		return mav;
	}
	
	@PostMapping("/new-category")
	String addNewCategory(
			@ModelAttribute 
			@Valid 
			CategoryRequestDto dto, 
			BindingResult result, 
			RedirectAttributes redirectAttrs) 
	{
		if(result.hasErrors()) {
			redirectAttrs.addFlashAttribute("error", "Adding new category failed.");
			return "redirect:/admin/categories";
		}
		Category savedCategory = categoryServ.save(new Category(dto.getName()));
		System.out.println(savedCategory);
		redirectAttrs.addFlashAttribute("success", savedCategory.getName() + " has been added.");
		return "redirect:/admin/categories";
	}
	
	@PostMapping("/update-category")
	String updateCategory(
			@ModelAttribute 
			@Valid 
			CategoryUpdateDto dto, 
			BindingResult result, 
			RedirectAttributes redirectAttrs) 
	{
		if(result.hasErrors()) {
			redirectAttrs.addFlashAttribute("error", "Updating category failed.");
			return "redirect:/admin/categories";
		}
		String newName = categoryServ.updateName(dto.getId(), dto.getName()).getName();
		redirectAttrs.addFlashAttribute("success", "Category name updated to "+newName);
		return "redirect:/admin/categories";
	}
	
	@RequestMapping(value = "/enable", method = { RequestMethod.GET, RequestMethod.POST })
	String enableCategoryById(
			@RequestParam 
			String id, 
			RedirectAttributes redirectAttrs) 
	{
		String name = categoryServ.enableById(id).getName();
		redirectAttrs.addFlashAttribute("success", name + " has been enabled.");
		return "redirect:/admin/categories";
	}
	

	@RequestMapping(value = "/disable", method = { RequestMethod.GET, RequestMethod.POST })
	String disableCategoryById(
			@RequestParam 
			String id, 
			RedirectAttributes redirectAttrs)
	{
		String name = categoryServ.disableById(id).getName();
		redirectAttrs.addFlashAttribute("success", name + " has been disabled.");
		return "redirect:/admin/categories";
	}
	
	@RequestMapping(value = "/enable-all", method = { RequestMethod.GET, RequestMethod.POST })
	String enableAllCategories(RedirectAttributes redirectAttrs) {
		
		categoryServ.enableAll();
		redirectAttrs.addFlashAttribute("success", "All Categories have been enabled.");
		return "redirect:/admin/categories";
	}
}
