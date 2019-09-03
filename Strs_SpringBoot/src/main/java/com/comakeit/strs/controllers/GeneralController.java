package com.comakeit.strs.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class GeneralController {
	
	@RequestMapping(value = "")
	public  ModelAndView gotoIndex() {
		ModelAndView modelAndView = new ModelAndView();
	
		modelAndView.setViewName("index.jsp?");

		return modelAndView;
	}
	
	@RequestMapping(value = "/redirectToShowAllTickets", method = RequestMethod.GET)
	   public String redirectToShowAllTickets() {
	      return "redirect:ServiceEngineer.jsp?operation=ShowAllTickets";
	   }
	
}
