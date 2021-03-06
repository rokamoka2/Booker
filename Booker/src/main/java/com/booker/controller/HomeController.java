package com.booker.controller;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.booker.domain.Book;
import com.booker.domain.Role;
import com.booker.domain.Shelf;
import com.booker.domain.User;
import com.booker.domain.Wardrobe;
import com.booker.service.BookService;
import com.booker.service.RoleService;
import com.booker.service.UserServiceImpl;

@Controller
public class HomeController {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	
	@Autowired
	private BookService bookService;
	
	@Autowired
	private UserServiceImpl userService;
	
	@Autowired
	private RoleService roleService;
	
	@RequestMapping("/")
	public String getIndex(Model model){
		List<Book> books = bookService.getBooks();
		model.addAttribute("pageTitle", "Könyv Adatbázis");	
		model.addAttribute("books", books);
		model.addAttribute("count", books.size());
		return "index";		
	}
	
	@RequestMapping("/mybooks")
	public String getMyBooks(Model model){
		User user = getAuthenticatedUser();
		Set<Book> books = new HashSet<>();
		model.addAttribute("pageTitle", "Könyv Adatbázis");
		for (Wardrobe wardrobe : user.getWardrobes()){
			for(Shelf shelf : wardrobe.getShelfs()){
				for(Book book : shelf.getBooks()){
					books.add(book);
				}
			}
		}	
		model.addAttribute("books", books);
		model.addAttribute("count", books.size());
		return "mybooks";		
	}
	
	@RequestMapping("/login")
	public String getLogin(Model model){
		model.addAttribute("pageTitle", "Könyv Adatbázis");	
		return "login";		
	}
	
	@RequestMapping("/regist")
	public String getRegist(Model model){
		model.addAttribute("pageTitle", "Könyv Adatbázis - Regisztráció");
		model.addAttribute("newuser", new User());
		return "regist";		
	}
	
	@RequestMapping("/add")
	public String getAdd(Model model) {
		model.addAttribute("pageTitle", "Könyv Hozzáadás");	
		model.addAttribute("book", new Book());
		return "edit";
	}
	
	@PostMapping("/savebook")
	public String addbook(@ModelAttribute Book book,Model model) {
		book.setNotedDate(new Date());
		bookService.SaveBook(book);
		return "redirect:/book/" + book.getId();
	}
	
	@RequestMapping("/admin")
	public String getAdmin(Model model){
		model.addAttribute("pageTitle", "Könyv Admin");	
		model.addAttribute("users", userService.getAllUser());
		model.addAttribute("roles", roleService.getRoles());
		model.addAttribute("newuser", new User());
		model.addAttribute("newrole", new Role());
		return "admin";		
	}
	
	@PostMapping("/adduser")
	public String adduser(@ModelAttribute User newuser,Model model) {
		userService.addUser(newuser);
		log.info("New user{}", newuser.getUsername());
		return this.getAdmin(model);
	}
	
	@RequestMapping("/edit/{ID}")
	public String getModify(@PathVariable(value="ID") Long id, Model model) {
		model.addAttribute("pageTitle", "Könyv Módosítás");	
		model.addAttribute("book", bookService.FindById(id));
		return "edit";
	}
	
	@PostMapping("/modifybook")
	public String getodifyBook(@ModelAttribute Book book,Model model) {
		log.info("Könyv módosítása: {}", book.getTitle() );
		bookService.UpdateBook(book);
		log.info("Saving {}", book.getTitle());
		return "redirect:/#" + book.getId();
	}
	
	@RequestMapping("/delete/{ID}")
	public String getDelete(@PathVariable(value="ID") Long id, Model model) {
		model.addAttribute("pageTitle", "Könyv Törlés");	
		log.info("Deleting {}" , bookService.FindById(id).getTitle());
		bookService.DeleteBook(bookService.FindById(id));
		return "redirect:/";
	}
	
	@RequestMapping("/search/{searched}")
	public String get(@PathVariable(value="searched") String searched, Model model){
		model.addAttribute("pageTitle", "Könyv Adatbázis Keresés");
		log.info("Searching {}", searched);
		List<Book> result = bookService.findBookByName(searched);
		model.addAttribute("books", result);
		model.addAttribute("count", "Ennyi könyvet találtam: " + result.size());
		return "index";		
	}
	
	@PostMapping("/addrole")
	public String adduser(@ModelAttribute Role newRole,Model model) {
		log.info("New role {}" , newRole.getName());
		roleService.Save(newRole);
		return this.getAdmin(model);
	}
	
	@RequestMapping("/deleteuser/{ID}")
	public String getDeleteUser(@PathVariable(value="ID") Long id, Model model) {
		model.addAttribute("pageTitle", "Felhasználó törlés");	
		userService.DeleteUser(userService.FindByID(id));
		return this.getAdmin(model);
	}

	private User getAuthenticatedUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return userService.findByUsername(auth.getName());
	}
}