package com.booker.controller;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.booker.domain.Book;
import com.booker.domain.User;
import com.booker.service.BookService;
import com.booker.service.DataService;
import com.booker.service.UserServiceImpl;

	@Controller
	public class BookController {
	
		@Autowired
		private BookService bookService;
		
		@Autowired
		private UserServiceImpl userService;
		
		@Autowired
		private DataService API;
		
		private final Logger log = LoggerFactory.getLogger(this.getClass());
	
		@RequestMapping("/claim/{ID}")
		public String claim(@PathVariable(value="ID") Long id, Model model) {
			User user = getAuthenticatedUser();
			user.getBooks().add(bookService.FindById(id));
			userService.saveUser(user);
			log.debug(bookService.FindById(id).getTitle() + " hozzáadva " + user.getUsername() + "felhasználóhoz!");
			return "redirect:/";
		}
		
		@RequestMapping("/book/{ID}")
		public String book(@PathVariable(value="ID") Long id, Model model) throws JSONException, IOException {
			boolean detailed = false;
			String description = "";
			String image = "";
	//		User user = getAuthenticatedUser();
			Book book = bookService.FindById(id);
			if ( book.getMolyId() == null && book.getIsbn() != null && !book.getIsbn().isEmpty()) {
				JSONObject obj = new JSONObject(API.GetBookByISBN(book.getIsbn()));
				book.setMolyId(obj.getLong("id"));
				bookService.SaveBook(book);
			}
			if ( book.getMolyId()!= null){
				JSONObject obj = new JSONObject(API.GetBookByMolyID(book.getMolyId()));
				description = obj.getJSONObject("book").getString("description");
				image = obj.getJSONObject("book").getString("cover");
				detailed = true;			
			}
			model.addAttribute("detailed", detailed);
			model.addAttribute("description", description);
			model.addAttribute("image", image);
			model.addAttribute("book", book);
			return "book";
		}
		
		private User getAuthenticatedUser() {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			User user = userService.findByUsername(auth.getName());
			return user;
			}

}