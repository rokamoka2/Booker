package com.booker.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.booker.domain.User;
import com.booker.service.BookService;
import com.booker.service.UserServiceImpl;

	@Controller
	public class BookController {
	
	@Autowired
	private BookService bookService;
	
	@Autowired
	private UserServiceImpl userService;
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@RequestMapping("/claim/{ID}")
	public String claim(@PathVariable(value="ID") Long id, Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findByUsername(auth.getName());
		user.getBooks().add(bookService.FindById(id));
		userService.saveUser(user);
		log.debug(bookService.FindById(id).getTitle() + " hozzáadva " + auth.getName() + "felhasználóhoz!");
		return "redirect:/";
	}

}
