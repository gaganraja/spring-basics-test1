package com.example.authenticatingldap;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

	@GetMapping("/")
	public String index() {
		return "Welcome to the home page!";
	}


	@GetMapping("/secure")
	public String getSecureData() {
		return "Welcome to the Secure Data page!";
	}

	@GetMapping("/all-users")
	public String getAllUsersData() {
		return "Welcome to the All users Data page!";
	}

}
