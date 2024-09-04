package dev.mrkevr.ecommerce.controller.admin;

import java.util.List;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
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
import dev.mrkevr.ecommerce.dto.ProductUpdateRequest;
import dev.mrkevr.ecommerce.entity.Category;
import dev.mrkevr.ecommerce.service.CategoryService;
import dev.mrkevr.ecommerce.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import static dev.mrkevr.ecommerce.constant.ModelAttributeConstant.*;

@Controller
@RequestMapping("/admin/products")
@RequiredArgsConstructor
@Slf4j
public class AdminProductController {
	
	private final ProductService productServ;
	private final CategoryService categoryServ;
	
	@ModelAttribute("categories")
	private List<Category> findAllActivatedCategories() {
		return categoryServ.findAllByActivatedTrue();
	}
	
	@GetMapping
	ModelAndView products() {
		ModelAndView mav = new ModelAndView("admin/products");
		mav.addObject(TITLE, "Products - Admin");
		mav.addObject(PRODUCTS, productServ.findAll());
		return mav;
	}
	
	@GetMapping("/category/{category}")
	ModelAndView productsByCategory(@PathVariable String category) {
		ModelAndView mav = new ModelAndView("admin/products-by-category");
		mav.addObject(TITLE, category + " - Admin");
		mav.addObject(CATEGORY, category);
		mav.addObject(PRODUCTS, productServ.getAllByCategory(category));
		return mav;
	}
	
	@GetMapping("/{id}")
	ModelAndView productDetails(@PathVariable String id) {
		ModelAndView mav = new ModelAndView("admin/product-details");
		ProductResponse product = productServ.getById(id);
		mav.addObject(PRODUCT, product);
		mav.addObject(TITLE, product.getName() + " - Admin");
		return mav;
	}
	
	@GetMapping("/new-product")
	ModelAndView newProduct() {
		ModelAndView mav = new ModelAndView("admin/new-product");
		mav.addObject(TITLE, "New Product - Admin");
		mav.addObject(PRODUCT_REQUEST, new ProductRequest());
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
			Model model) {
		
		model.addAttribute(TITLE, "New Product - Admin");
		
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
				model.addAttribute(PRODUCT_IMAGE_ERROR, "Image is too large.");
				return "/admin/new-product"; 
			}
		}
		
		try {
			ProductResponse response = productServ.save(productRequest, productImage);
			redirectAttrs.addFlashAttribute(SUCCESS, "New product has been added!");
			return "redirect:/admin/products/"+response.getId();
		} catch (Exception e) {
            log.error(e.getMessage(), e.getCause());
            redirectAttrs.addFlashAttribute(ERROR, "Failed to add new product!");
            redirectAttrs.addFlashAttribute(TITLE, "New Product - Admin");
            return "/admin/new-product";
        }
	}
	
	@GetMapping("/update")
	ModelAndView updateProduct(@RequestParam String id) {
		ModelAndView mav = new ModelAndView("admin/update-product");
		ProductUpdateRequest productUpdateRequest = productServ.getUpdateRequestById(id);
		mav.addObject("productUpdateRequest", productUpdateRequest);
		mav.addObject(TITLE, "Update Product - Admin");
		return mav;
	}
	
	@PostMapping(value = "/process-update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	String updateProduct(
			@RequestParam("productImage") 
			MultipartFile productImage,
			@Valid 
			@ModelAttribute("productUpdateRequest") 
			ProductUpdateRequest productUpdateRequest,
			BindingResult result, 
			RedirectAttributes redirectAttrs,
			Model model) {
		
		model.addAttribute(TITLE, "Update Product - Admin");
		
		if(result.hasErrors()) {
			return "/admin/update-product";
		}
		
		try {
			ProductResponse response = productServ.update(productUpdateRequest.getId(), productUpdateRequest, productImage);
			redirectAttrs.addFlashAttribute(SUCCESS, "Product has been updated!");
			return "redirect:/admin/products/"+response.getId();
		} catch (Exception e) {
			log.error(e.getMessage(), e.getCause());
            redirectAttrs.addFlashAttribute(ERROR, "Failed to add new product!");
            redirectAttrs.addFlashAttribute(TITLE, "Update - Admin");
            return "/admin/update-product";
        }
	}
	
	@RequestMapping(value = "/enable", method = { RequestMethod.GET, RequestMethod.POST })
	String enableProductById(
			@RequestParam 
			String id, 
			RedirectAttributes redirectAttrs,
			HttpServletRequest request) {
		
		String name = productServ.enableById(id).getName();
		redirectAttrs.addFlashAttribute(SUCCESS, name + " has been enabled.");
		return getPreviousPageByRequest(request).orElse("/redirect:/admin/products");
	}
	

	@RequestMapping(value = "/disable", method = { RequestMethod.GET, RequestMethod.POST })
	String disableProductById(
			@RequestParam 
			String id, 
			RedirectAttributes redirectAttrs,
			HttpServletRequest request) {
		
		String name = productServ.disableById(id).getName();
		redirectAttrs.addFlashAttribute(SUCCESS, name + " has been disabled.");
		return getPreviousPageByRequest(request).orElse("/redirect:/admin/products");
	}
	
	@RequestMapping(value = "/turnOnSale", method = { RequestMethod.GET, RequestMethod.POST })
	String setProductOnSaleById(
			@RequestParam 
			String id, 
			RedirectAttributes redirectAttrs,
			HttpServletRequest request) {
		
		String name = productServ.putOnSaleById(id).getName();
		redirectAttrs.addFlashAttribute(SUCCESS, name + " is now on sale.");
		return getPreviousPageByRequest(request).orElse("/redirect:/admin/products");
	}
	
	@RequestMapping(value = "/turnOffSale", method = { RequestMethod.GET, RequestMethod.POST })
	String setProductOffSaleById(
			@RequestParam 
			String id, 
			RedirectAttributes redirectAttrs,
			HttpServletRequest request) {
		
		String name = productServ.putOffSaleById(id).getName();
		redirectAttrs.addFlashAttribute(SUCCESS, name + " is now off sale.");
		return getPreviousPageByRequest(request).orElse("/redirect:/admin/products");
	}
	
	/**
	* Returns the viewName to return for coming back to the sender url
	*
	* @param request Instance of {@link HttpServletRequest} or use an injected instance
	* @return Optional with the view name. Recomended to use an alternativa url with
	* {@link Optional#orElse(java.lang.Object)}
	*/
	protected Optional<String> getPreviousPageByRequest(HttpServletRequest request) {
		return Optional.ofNullable(request.getHeader("Referer")).map(requestUrl -> "redirect:" + requestUrl);
	}
}
