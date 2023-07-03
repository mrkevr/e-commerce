package dev.mrkevr.ecommerce.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/about")
public class AboutController {
	
	@GetMapping
	ModelAndView about() {
		ModelAndView mav = new ModelAndView("about");
		mav.addObject("title", "About - E-Commerce");
		return mav;
	}
}
