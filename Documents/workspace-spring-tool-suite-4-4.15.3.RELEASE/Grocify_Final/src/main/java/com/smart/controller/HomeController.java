package com.smart.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.smart.dao.UserRepository;
import com.smart.entities.User;
import com.smart.helper.Message;

@Controller
public class HomeController {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private UserRepository userRepository;

	@RequestMapping("/")
	public String login() {
		return "home2";
	}

	@RequestMapping("/aboutus")
	public String aboutus() {
		return "home";
	}

	@RequestMapping("/register")
	public String signup(Model model) {
		model.addAttribute("user", new User());
		return "register";
	}

	// handler for registering user
	@PostMapping("/register")
	public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult result1, Model model,
			HttpSession session) {
		try {
			if (result1.hasErrors()) {
				System.out.println("ERROR" + result1.toString());
				model.addAttribute("user", user);
				return "register";
			}
			user.setRole("ROLE_USER");
			user.setPassword(passwordEncoder.encode(user.getPassword()));

			System.out.println("USER" + user);

			User result = this.userRepository.save(user);

			model.addAttribute("user", new User());
			session.setAttribute("message", new Message("Successfully registered", "success"));
			return "register";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("user", user);
			session.setAttribute("message", new Message("something went wrong !!" + e.getMessage(), "alert-danger"));
			return "register";
		}
	}

	// handler for custom login
	@GetMapping("/login")
	public String customLogin(Model model) {
		model.addAttribute("title", "login-page");
		return "login";
		
	}
}
