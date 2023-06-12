package dev.mrkevr.ecommerce.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import dev.mrkevr.ecommerce.servioe.impl.AdminServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HomeController {

	private final AdminServiceImpl adminServ;

//	@GetMapping({ "/", "/dashboard" })
//	ModelAndView displayDashboard(HttpServletRequest request) {
//		
//		// Redirects to admin dashboard if the user's role is ADMIN
//		if (request.isUserInRole("ROLE_ADMIN")) {
//			ModelAndView mav = new ModelAndView("admin/admin-dashboard");
//			mav.addObject("title", "Dashboard - Admin");
//			mav.addObject("totalUsers", adminServ.getTotalUsers());
//			mav.addObject("totalCategories", adminServ.getTotalCategories());
//			mav.addObject("totalProducts", adminServ.getTotalProducts());
//			mav.addObject("totalOrders", adminServ.getTotalOrders());
//			return mav;
//		}
//
//		ModelAndView mav = new ModelAndView("index");
//		return mav;
//	}
	
	@GetMapping({ "/", "/dashboard" })
	ModelAndView displayDashboard(HttpServletRequest request) {
		
		// Redirects to admin dashboard if the user's role is ADMIN
		if (request.isUserInRole("ROLE_ADMIN")) {
			return new ModelAndView("redirect:/admin/dashboard");
		}

		ModelAndView mav = new ModelAndView("index");
		return mav;
	}
	
	@GetMapping({"/admin/dashboard"})
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	ModelAndView dashboard() {

		ModelAndView mav = new ModelAndView("admin/admin-dashboard");
		mav.addObject("title", "Dashboard - Admin");
		mav.addObject("totalUsers", adminServ.getTotalUsers());
		mav.addObject("totalCategories", adminServ.getTotalCategories());
		mav.addObject("totalProducts", adminServ.getTotalProducts());
		mav.addObject("totalOrders", adminServ.getTotalOrders());

		return mav;
	}
}
