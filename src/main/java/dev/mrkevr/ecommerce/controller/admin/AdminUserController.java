package dev.mrkevr.ecommerce.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import dev.mrkevr.ecommerce.servioe.UserService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminUserController {

	private final UserService userServ;

	@GetMapping("/users")
	ModelAndView dashboard() {

		ModelAndView mav = new ModelAndView("admin/users");
		mav.addObject("users", userServ.getAllUsersDto());

		return mav;
	}

	@GetMapping("/users/{id}")
	ModelAndView dashboard(@PathVariable String id) {

		ModelAndView mav = new ModelAndView("admin/user-details");
		mav.addObject("user", userServ.getProfileDto(id));
		
		System.out.println(userServ.getProfileDto(id));
		
		return mav;
	}
}
