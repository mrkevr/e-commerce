package dev.mrkevr.ecommerce.controller.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dev.mrkevr.ecommerce.dto.ProductRequest;
import dev.mrkevr.ecommerce.dto.ProductResponse;
import dev.mrkevr.ecommerce.entity.Category;
import dev.mrkevr.ecommerce.servioe.CategoryService;
import dev.mrkevr.ecommerce.servioe.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin/products")
@RequiredArgsConstructor
public class AdminProductController {
	
	private final ProductService productServ;
	private final CategoryService categoryServ;
	
	@ModelAttribute("categories")
	private List<Category> findAllActivatedCategories() 
	{
		return  categoryServ.findAllByActivatedTrue();
	}
	
	@GetMapping
	ModelAndView products() 
	{
		ModelAndView mav = new ModelAndView("admin/products");
		mav.addObject("title", "Products - Admin");
		mav.addObject("products", productServ.findAll());
		return mav;
	}
	
	@GetMapping("/category/{category}")
	ModelAndView productsByCategory(@PathVariable String category)
	{
		ModelAndView mav = new ModelAndView("admin/products-by-category");
		mav.addObject("title", category+" - Admin");
		mav.addObject("category", category);
		mav.addObject("products", productServ.findAllByCategory(category));
		return mav;
	}
	
	@GetMapping("/{id}")
	ModelAndView productDetails(@PathVariable String id) 
	{
		ModelAndView mav = new ModelAndView("admin/product-details");
		mav.addObject("title", "Product Details - Admin");
		mav.addObject("product", productServ.getById(id));
		return mav;
	}
	
	@GetMapping("/new-product")
	ModelAndView newProduct() 
	{
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
			RedirectAttributes redirectAttrs,
			Model model) 
	{
		
		
		model.addAttribute("title", "New Product - Admin");
		if(result.hasErrors()) {
			result.getAllErrors().forEach(e -> System.out.println(e.getDefaultMessage()));
			
			return "/admin/new-product";
		}
		
		if(productImage.isEmpty()) {
			model.addAttribute("productImageError", "Must upload product photo.");
			return "/admin/new-product";
		} else {
			long sizeInKb = productImage.getSize() / 1024;
			if(sizeInKb > 800) {
				model.addAttribute("productImageError", "Image is too large."); 
				return "/admin/new-product"; 
			}
		}
		
		try {
			ProductResponse response = productServ.save(productRequest, productImage);
			redirectAttrs.addFlashAttribute("success", "New product has been added!");
			return "redirect:/admin/products/"+response.getId();
		} catch (Exception e) {
            e.printStackTrace();
            redirectAttrs.addFlashAttribute("error", "Failed to add new product!");
            redirectAttrs.addFlashAttribute("title", "New Product - Admin");
            return "/admin/new-product";
        }
	}
	
	@RequestMapping(value = "/enable", method = { RequestMethod.GET, RequestMethod.POST })
	String enableProductById(
			@RequestParam 
			String id, 
			RedirectAttributes redirectAttrs,
			HttpServletRequest request)
	{
		String name = productServ.enableById(id).getName();
		redirectAttrs.addFlashAttribute("success", name + " has been enabled.");
		
		return getPreviousPageByRequest(request).orElse("/redirect:/admin/products");
	}
	

	@RequestMapping(value = "/disable", method = { RequestMethod.GET, RequestMethod.POST })
	String disableProductById(
			@RequestParam 
			String id, 
			RedirectAttributes redirectAttrs,
			HttpServletRequest request)
	{
		String name = productServ.disableById(id).getName();
		redirectAttrs.addFlashAttribute("success", name + " has been disabled.");
		
		return getPreviousPageByRequest(request).orElse("/redirect:/admin/products");
	}
	
	@RequestMapping(value = "/turnOnSale", method = { RequestMethod.GET, RequestMethod.POST })
	String setProductOnSaleById(
			@RequestParam 
			String id, 
			RedirectAttributes redirectAttrs,
			HttpServletRequest request)
	{
		String name = productServ.putOnSaleById(id).getName();
		redirectAttrs.addFlashAttribute("success", name + " is now on sale.");
		
		return getPreviousPageByRequest(request).orElse("/redirect:/admin/products");
	}
	
	@RequestMapping(value = "/turnOffSale", method = { RequestMethod.GET, RequestMethod.POST })
	String setProductOffSaleById(
			@RequestParam 
			String id, 
			RedirectAttributes redirectAttrs,
			HttpServletRequest request)
	{
		String name = productServ.putOffSaleById(id).getName();
		redirectAttrs.addFlashAttribute("success", name + " is now off sale.");
		
		return getPreviousPageByRequest(request).orElse("/redirect:/admin/products");
	}
	
	/**
	* Returns the viewName to return for coming back to the sender url
	*
	* @param request Instance of {@link HttpServletRequest} or use an injected instance
	* @return Optional with the view name. Recomended to use an alternativa url with
	* {@link Optional#orElse(java.lang.Object)}
	*/
	protected Optional<String> getPreviousPageByRequest(HttpServletRequest request)
	{
	   return Optional.ofNullable(request.getHeader("Referer")).map(requestUrl -> "redirect:" + requestUrl);
	}
}
