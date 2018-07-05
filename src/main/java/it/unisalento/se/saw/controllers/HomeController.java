package it.unisalento.se.saw.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.unisalento.se.saw.IService.IAulaService;
import it.unisalento.se.saw.IService.IUserService;
import it.unisalento.se.saw.domain.User;
import it.unisalento.se.saw.services.StudentService;

@Controller
public class HomeController {
	
	@Autowired
	StudentService studentService;
	
	@Autowired
	IUserService userService;
	
	@Autowired
	IAulaService aulaService;
	
	@Autowired
	public HomeController() {
		super();
	}
	
	public HomeController(IUserService userService) {
		this.userService = userService;
	}

	@RequestMapping(value="/home/users", method=RequestMethod.GET)
	public String users(ModelMap modelMap) {
		List<User> users = userService.getAll();
		modelMap.addAttribute("users", users);
		return "users";
		
	}
	
	@RequestMapping(value="/home", method=RequestMethod.GET)
	public String home(ModelMap modelMap) {
		
		User user = new User();
		user.setEmail("osvaldo@email.it");
		user.setName("Osvaldo");
		user.setSurname("Rossi");
		userService.save(user);
		
		studentService.getStudentById(7);
		modelMap.addAttribute("param1","param2");
		System.out.println("HOMECONTROLLER");
		return "home";
	}

}
