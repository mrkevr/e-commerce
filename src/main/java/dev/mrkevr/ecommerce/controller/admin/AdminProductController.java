package dev.mrkevr.ecommerce.controller.admin;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dev.mrkevr.ecommerce.dto.ProductRequest;
import dev.mrkevr.ecommerce.entity.Category;
import dev.mrkevr.ecommerce.servioe.CategoryService;
import dev.mrkevr.ecommerce.servioe.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin/products")
@RequiredArgsConstructor
public class AdminProductController {
	
	private final ProductService productServ;
	private final CategoryService categoryServ;
	
	@ModelAttribute("categories")
	private List<Category> findAllActivatedCategories() {
		return  categoryServ.findAllByActivatedTrue();
	}
	
	@GetMapping
	ModelAndView products() {

		ModelAndView mav = new ModelAndView("admin/products");
		mav.addObject("title", "Products - Admin");
		mav.addObject("products", productServ.findAll());
		return mav;
	}
	
	
	@GetMapping("/new-product")
	ModelAndView newProduct() {
		
		ModelAndView mav = new ModelAndView("admin/new-product");
		mav.addObject("title", "New Product - Admin");
		mav.addObject("productRequest", new ProductRequest());
		return mav;
	}
	
	@PostMapping(value = "/process-new-product", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	String addNewProduct(
			@RequestParam("productImage") 
			MultipartFile productImage,
			@Valid 
			@ModelAttribute("productRequest") 
			ProductRequest productRequest,
			BindingResult result, 
			RedirectAttributes redirectAttrs) 
	{
		System.out.println(productRequest);
		System.out.println(productImage.getOriginalFilename());
		
		if(result.hasErrors()) {
			redirectAttrs.addFlashAttribute("error", "Adding new product failed.");
			redirectAttrs.addFlashAttribute("title", "New Product - Admin");
			return "/admin/new-product";
		}
		
		try {
			productServ.save(productRequest, productImage);
			redirectAttrs.addFlashAttribute("success", "New product has been added!");
		} catch (Exception e) {
            e.printStackTrace();
            redirectAttrs.addFlashAttribute("error", "Failed to add new product!");
        }
		
		
		return "redirect:/admin/products";
	}
}
