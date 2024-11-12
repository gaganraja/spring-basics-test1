package com.example.authenticatingldap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableMethodSecurity
public class AuthenticatingLdapApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthenticatingLdapApplication.class, args);
	}

}
