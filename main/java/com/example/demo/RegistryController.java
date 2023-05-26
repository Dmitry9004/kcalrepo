package com.example.demo;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;

import jakarta.validation.Valid;

@Controller
public class RegistryController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private UsersRepository userRepo;
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	@PostMapping("/login")
	public String postLogin(){
		
		return "redirect:/homePageUser";
	}
	
	
	@GetMapping("/registration")
	public String getRegistry(Model model) {
		model.addAttribute("registration", new UserDto());
		
		return "registration";
	}
	
	@PostMapping("/registration")
	public String postRegistration(@Valid @ModelAttribute("registration") UserDto user, BindingResult res, Model model, SessionStatus status) {
		if(res.hasErrors()) {
			return "registration";
		}
		if(!user.getPassword().equals(user.getPasswordConfirm())) {
			model.addAttribute("passwordError","пароли не совпадают");
			return  "registration";
		}
		User userExist = userRepo.findByUsername(user.getUsername());
		
		if(userExist != null) {
			model.addAttribute("userError","пользователь с таким именем уже существует");
			return "registration";
		}
		
		User userS = new User();
		userService.saveUser(userS,user);
		return "redirect:/login";
	}	
}