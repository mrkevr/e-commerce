package dev.mrkevr.ecommerce.controller.admin;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import dev.mrkevr.ecommerce.dto.LoggedInUserDetails;
import dev.mrkevr.ecommerce.entity.User;
import dev.mrkevr.ecommerce.servioe.ApplicationUserManager;
import dev.mrkevr.ecommerce.servioe.impl.AdminServiceImpl;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminDashboardController {

	private final ApplicationUserManager applicationUserManager;
	private final AdminServiceImpl adminServ;
	
	
	@GetMapping("/dashboard")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	ModelAndView dashboard() {
		
		ModelAndView mav = new ModelAndView("admin/admin-dashboard");
		mav.addObject("totalUsers", adminServ.getTotalUsers());
		mav.addObject("totalCategories", adminServ.getTotalCategories());
		mav.addObject("totalProducts", adminServ.getTotalProducts());
		mav.addObject(null, mav)
		
		return mav;
	}

}
