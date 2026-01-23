package com.practice.microservices.identity_service.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.practice.microservices.identity_service.dtos.RegisterDto;
import com.practice.microservices.identity_service.services.UserService;
import com.practice.microservices.identity_service.utility.Result;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class UserController {
	
	private final UserService userService;
	
	 @PostMapping("/api/v1/auth/register")
	 public Result registerUser(@RequestBody RegisterDto register)
	 {
		 return this.userService.registerUser(register);
	 }
}
