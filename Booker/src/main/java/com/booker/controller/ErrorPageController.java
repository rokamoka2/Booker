package com.booker.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.ServletWebRequest;

@Controller
public class ErrorPageController implements ErrorController {
	
//	private static final String ERR_PATH = "/error";
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	
	private ErrorAttributes errorAttributes;
	
	@Autowired
	public void setErrorAttributes(ErrorAttributes errorAttributes) {
		this.errorAttributes = errorAttributes;
	}

	@Override
	public String getErrorPath() {
		// TODO Auto-generated method stub
		return "/error";
	}
	
	@RequestMapping("/error")
	public String error(Model model, HttpServletRequest request) {
		ServletWebRequest rA = new ServletWebRequest(request);
		Map<String, Object> error = this.errorAttributes.getErrorAttributes(rA, true);
		model.addAllAttributes(error);
//		log.debug("HIBAAAA: " + error.get("error") + " Üzenet: " + error.get("message") + " Hibakód: " + error.get("status"));
		int errorNum = (int) error.get("status");
		switch (errorNum) 
		{
		case 404:
			model.addAttribute("danger","Hibakód: " + errorNum + ". A keresett oldal nem található!");
			break;
		case 403:
			model.addAttribute("danger","Hibakód: " + errorNum + ". Hozzáférés megtagadva!");
			break;
		default:
			model.addAttribute("danger","Hibakód: " + errorNum + ". Egyéb hiba történt: " + error.get("message"));		
		}
		return "errors/detailedError";
	}

}
