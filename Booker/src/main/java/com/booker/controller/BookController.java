package com.booker.controller;

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
import com.booker.domain.Shelf;
import com.booker.domain.User;
import com.booker.service.BookService;
import com.booker.service.DataService;
import com.booker.service.ShelfService;
import com.booker.service.UserServiceImpl;

	@Controller
	public class BookController {
	
		@Autowired
		private BookService bookService;
		
		@Autowired
		private UserServiceImpl userService;
		
		@Autowired
		private DataService dService;

		@Autowired
		private ShelfService sService;
		
		private final Logger log = LoggerFactory.getLogger(this.getClass());
	
		@RequestMapping("/claim/{shelfId}/{bookId}")
		public String claim(@PathVariable(value="bookId") Long bookId, @PathVariable(value = "shelfId") Long shelfId, Model model) {
			User user = getAuthenticatedUser();
			Book book = bookService.FindById(bookId);
			Shelf shelf = sService.getById(shelfId);
			if(user.getUsername().equals(shelf.getWardrobe().getOwner().getUsername())){
				shelf.getBooks().add(book);
			}
			sService.saveShelf(shelf);
			log.debug("{} claimed {} book", user.getUsername(), book.getTitle());
			return "redirect:/";
		}
		
		@RequestMapping("/book/{ID}")
		public String book(@PathVariable(value="ID") Long id, Model model) throws Exception {
			boolean detailed = false;
			String description = "";
			String image = "";
			Book book = bookService.FindById(id);
			if ( book.getMolyId() == null && book.getIsbn() != null && !book.getIsbn().isEmpty()) {
				try {
				JSONObject obj = new JSONObject(dService.getBookByISBN(book.getIsbn()));
				book.setMolyId(obj.getLong("id"));
				bookService.SaveBook(book);
					
				} catch (Exception e) {
					log.debug("Hiba az ISBN lekérdezésben: {}" , e.getMessage());
					
				}
			}
			if ( book.getMolyId()!= null){
				try {
					JSONObject obj = new JSONObject(dService.getBookByMolyID(book.getMolyId()));
					description = obj.getJSONObject("book").getString("description");
					image = obj.getJSONObject("book").getString("cover");
					detailed = true;
				} catch (Exception e) {
					log.info("Hibás MolyID: {}" , book.getMolyId());
					book.setMolyId(null);
					bookService.SaveBook(book);
					log.info("Hibás MolyID törölve.");
					book(id, model);					
				}
							
			}
			model.addAttribute("detailed", detailed);
			model.addAttribute("description", description);
			model.addAttribute("image", image);
			model.addAttribute("book", book);
			return "book";
		}
		
		private User getAuthenticatedUser() {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			return userService.findByUsername(auth.getName());
			}

}
