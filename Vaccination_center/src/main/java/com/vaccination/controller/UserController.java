package com.vaccination.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vaccination.entity.User;
import com.vaccination.repository.UserRepository;
import com.vaccination.service.UserService;

@Controller
@RequestMapping("/")
public class UserController {

	private final UserService userService;
	private final UserRepository userRepository;

	public UserController(UserService userService, UserRepository userRepository) {
		this.userService = userService;
		this.userRepository = userRepository;
	}

	@GetMapping
	public String showRLoginPage() {
		return "redirect:/login.html";
	}

	@PostMapping("/login")
	public String processLogin(@RequestParam("username") String username, @RequestParam("password") String password) {

		User user = userRepository.findByUsername(username);

		// Check if the user exists and the password matches
		if (user != null && user.getPassword().equals(password)) {
			// Redirect to the user dashboard or any other desired page
			return "redirect:/vaccine-centers";
		} else {

			return "redirect:/errorpage.html";
		}
	}

	@GetMapping("/register")
	public String showRegisterPage() {
		return "redirect:/register.html";
	}

	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@RequestParam("username") String username,
			@RequestParam("email") String email, @RequestParam("password") String password) {
		User user = new User(username, email, password);
		userService.registerUser(user);
		return ResponseEntity.ok("User registered successfully");
	}
}
