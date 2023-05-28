package dev.mrkevr.ecommerce.controller.handler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ErrorControllerHandler {

	@GetMapping("/error")
	public ModelAndView renderErrorPage(HttpServletRequest httpRequest) {

		int httpErrorCode = getErrorCode(httpRequest);

		switch (httpErrorCode) {
		case 403:
			return new ModelAndView("403");
		case 401:
			return new ModelAndView("401");
		case 404:
			return new ModelAndView("404");
		case 500:
			return new ModelAndView("500");
		}
		return null;
	}

	private int getErrorCode(HttpServletRequest httpRequest) {
		return (Integer) httpRequest.getAttribute("javax.servlet.error.status_code");
	}

}
