package com.example.demo;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.ServletContext;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/")
@SessionAttributes("calories")
public class BaseController {

	
	private UsersRepository userRepo;
	private UserService userService;
	
	public BaseController(UsersRepository userRepo, UserService userService) {
		this.userRepo = userRepo;
		this.userService = userService;
	}
	
	@GetMapping
	public String getHome(Model model) {
		model.addAttribute("calories", new Calories());
		return "home";
	}
	  
	  @GetMapping("/calc")
	  public String goCalc(@Valid @ModelAttribute("calories") Calories calc, BindingResult errors, Model model)
	  { 
		  if(errors.hasErrors() || calc == null){return "home";}
		  
	  	model.addAttribute("BMI", calc.countBMI());
	  	model.addAttribute("caloriesCalcNormal", calc.getCalories());	
	  	model.addAttribute("caloriesCalcMin", calc.getCaloriesMin());
	  	model.addAttribute("caloriesCalcMax", calc.getCaloriesMax());
	  	return "calc";
	  }
	  
	  @GetMapping("/homePageUser")
	  public String toHomePageUser(Principal principal, Model model) {
		  if(principal == null) {
			  return "redirect:/login";
		  }
		  User userAuth = userRepo.findByUsername(principal.getName());
		  if(userAuth == null) {
			  return "redirect:/login";
		  }
		  model.addAttribute("userCalc", userAuth.getCountCalories());
		  model.addAttribute("userCalcTarget", userAuth.getTarget());
		  model.addAttribute("userName", userAuth.getUsername());
		  model.addAttribute("BMI", userAuth.getBMI());
		  
		  return "homePageUser";
	  }
	  
	  @GetMapping("/settings")
	  public String updateUserSettingGet(Principal principal, Model model) {		
		  

			
		  User user = userRepo.findByUsername(principal.getName());
		  UserDto userDto = new UserDto(user.getUsername(),user.getAge(), user.getGrowth(),user.getWeight(), user.getCountAction(), user.getTarget(),user.getGender());
		  
		  model.addAttribute("userDef", userDto);
		  return "settings";
	  }
	 	
	  @PostMapping("/settings")
	  public String updateUserSettingsPost(Principal principal, @Valid @ModelAttribute("userDef") UserDto user, BindingResult error, Model model) {
		  
		  if(!user.getPassword().equals(user.getPasswordConfirm())) {
				model.addAttribute("passwordError","пароли не совпадают");
				return  "settings";
			}
			User userExist = userRepo.findByUsername(user.getUsername());
			User userAuth = userRepo.findByUsername(principal.getName());
			
			if(userExist != null && !userExist.getUsername().equals(userAuth.getUsername())) {
				model.addAttribute("userError","пользователь с таким именем уже существует");
				return "settings";
			}
		  if(error.hasErrors()) {
			  return "settings";
		  }
		  User userF = userRepo.findByUsername(principal.getName());
		  userService.updateUser(userF.getId(), user);
		  
		  
		  return "redirect:/homePageUser";
	  }
}
