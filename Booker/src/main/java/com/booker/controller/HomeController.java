package com.booker.controller;

import java.util.Date;
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
import com.booker.domain.User;
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
	public String Index(Model model){
		List<Book> books = bookService.getBooks();
		model.addAttribute("pageTitle", "Könyv Adatbázis");	
		model.addAttribute("books", books);
		model.addAttribute("count", books.size());
		return "index";		
	}
	
	@RequestMapping("/mybooks")
	public String MyBooks(Model model){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		model.addAttribute("pageTitle", "Könyv Adatbázis");	
		Set books = userService.findByUsername(auth.getName()).getBooks();		
		model.addAttribute("books", books);
		model.addAttribute("count", books.size());
		return "mybooks";		
	}
	
	@RequestMapping("/login")
	public String Login(Model model){
		model.addAttribute("pageTitle", "Könyv Adatbázis");	
		return "login";		
	}
	
	@RequestMapping("/regist")
	public String Regist(Model model){
		model.addAttribute("pageTitle", "Könyv Adatbázis - Regisztráció");
		model.addAttribute("newuser", new User());
		return "regist";		
	}
	
	@RequestMapping("/add")
	public String Add(Model model) {
		model.addAttribute("pageTitle", "Könyv Hozzáadás");	
		model.addAttribute("book", new Book());
		return "add";
	}
	
	@PostMapping("/addbook")
	public String addbook(@ModelAttribute Book book,Model model) {
		System.out.println("ÚJ könyv " + book.getTitle() );
		book.setNotedDate(new Date());
//		log.info(book.getUser().getUsername());
		bookService.SaveBook(book);
		log.info(book.getTitle() + " elmentve!");
		return this.Index(model);
	}
	
	@RequestMapping("/admin")
	public String Admin(Model model){
		model.addAttribute("pageTitle", "Könyv Admin");	
		model.addAttribute("users", userService.getAllUser());
		model.addAttribute("roles", roleService.getRoles());
		model.addAttribute("newuser", new User());
		model.addAttribute("newrole", new Role());
		return "admin";		
	}
	
	@PostMapping("/adduser")
	public String adduser(@ModelAttribute User newuser,Model model) {
		log.info("ÚJ user " + newuser.getUsername() );
		userService.addUser(newuser);
		log.info(newuser.getUsername() + " elmentve!");
		return this.Admin(model);
	}
	
	@RequestMapping("/modify/{ID}")
	public String Modify(@PathVariable(value="ID") Long id, Model model) {
		model.addAttribute("pageTitle", "Könyv Módosítás");	
		model.addAttribute("book", bookService.FindById(id));
		return "modify";
	}
	
	@PostMapping("/modifybook")
	public String ModifyBook(@ModelAttribute Book book,Model model) {
		log.info("Könyv módosítás " + book.getTitle() );
		bookService.UpdateBook(book);
		log.info(book.getTitle() + " elmentve!");
		return "redirect:/#" + book.getId();
	}
	
	@RequestMapping("/delete/{ID}")
	public String Delete(@PathVariable(value="ID") Long id, Model model) {
		model.addAttribute("pageTitle", "Könyv Törlés");	
		log.info(bookService.FindById(id).getTitle() + " törlése!");
		bookService.DeleteBook(bookService.FindById(id));
		return this.Index(model);
	}
	
	@RequestMapping("/search/{searched}")
	public String Search(@PathVariable(value="searched") String searched, Model model){
		model.addAttribute("pageTitle", "Könyv Adatbázis Keresés");
		log.info("Új keresés erre: " + searched);
		List<Book> result = bookService.findBookByName(searched);
		model.addAttribute("books", result);
		model.addAttribute("count", "Ennyi könyvet találtam: " + result.size());
		return "index";		
	}
	
	@PostMapping("/addrole")
	public String adduser(@ModelAttribute Role newRole,Model model) {
		log.info("ÚJ role " + newRole.getName());
		roleService.Save(newRole);
		log.info(newRole.getName() + " elmentve!");
		return this.Admin(model);
	}
	
	@RequestMapping("/deleteuser/{ID}")
	public String DeleteUser(@PathVariable(value="ID") Long id, Model model) {
		model.addAttribute("pageTitle", "Felhasználó törlés");	
		userService.DeleteUser(userService.FindByID(id));
		return this.Admin(model);
	}
	
		
}
