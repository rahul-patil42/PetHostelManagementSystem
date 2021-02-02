package com.app.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.app.dao.UserRepository;
import com.app.entities.User;
import com.app.helper.Message;

@Controller
public class RegistrationController {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	/* signup handler */
	@RequestMapping("/signup")
	public String signup(Model model) {
		model.addAttribute("title", "Register");
		model.addAttribute("user", new User());
		return "signup";
	}

	/* Handler for Registering customer */
	@RequestMapping(value = "/do_register", method = RequestMethod.POST)
	public String registerCustomer(@Valid @ModelAttribute("user") User user, BindingResult result1, Model model,
			@RequestParam(value = "agreement", defaultValue = "false") boolean agreement, HttpSession session) {

		try {
			if (!agreement) {
				System.out.println("Not agreed to terms");
				throw new Exception("Not agreed to terms");
			}

			if (result1.hasErrors()) {
				System.out.println("ERROR " + result1.toString());
				model.addAttribute("user", user);
				return "signup";
			}

			user.setRole("ROLE_USER");
			user.setPassword(passwordEncoder.encode(user.getPassword()));

			System.out.println("Agreement:" + agreement);
			System.out.println("Customer:" + user);

			User result = this.userRepository.save(user);

			model.addAttribute("user", new User()); /* else condition if checkbox is checked */
			session.setAttribute("message", new Message("Successfully Registered...!", "alert-success"));
			return "signup";

		} catch (Exception e) {
			e.printStackTrace();

			model.addAttribute("user", user);
			session.setAttribute("message", new Message("Something went wrong... " + e.getMessage(), "alert-danger"));

			return "signup";
		}
	}

	//handler for custom login
	@GetMapping("/signin")
	public String customLogin(Model model) {
		
		model.addAttribute("title", "Sign In");
		return "login";
	}
}
