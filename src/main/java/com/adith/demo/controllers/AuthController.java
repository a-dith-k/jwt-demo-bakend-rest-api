package com.adith.demo.controllers;

import com.adith.demo.exceptions.UserAlreadyExistsException;
import com.adith.demo.models.RegistrationResponse;
import com.adith.demo.services.user.UserService;
import com.adith.demo.models.RegistrationRequest;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import com.adith.demo.services.jwt.JwtService;
import com.adith.demo.models.JwtRequest;
import com.adith.demo.models.JwtResponse;


@Slf4j
@RestController
@RequestMapping("auth")
public class AuthController{



	final private UserService userService;

	public AuthController( UserService userService) {

		this.userService = userService;
	}

	@PostMapping("login")
	public ResponseEntity<JwtResponse> authenticate(@RequestBody JwtRequest request) {
			String token
					=userService.getAuthenticationToken(request);

		return ResponseEntity
				.status(HttpStatus.OK)
				.body(new JwtResponse(request.username(),token));
	}

	@PostMapping("/register")
	public ResponseEntity<RegistrationResponse> registerUser(@RequestBody RegistrationRequest request) throws UserAlreadyExistsException {

		RegistrationResponse response
				= userService.registerUser(request);
		return  ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	

}