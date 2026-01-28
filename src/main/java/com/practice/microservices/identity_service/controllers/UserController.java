package com.practice.microservices.identity_service.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.practice.microservices.identity_service.dtos.RegisterDto;
import com.practice.microservices.identity_service.entity.UserEntity;
import com.practice.microservices.identity_service.services.UserService;
import com.practice.microservices.identity_service.utility.Result;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class UserController {
	
	private final UserService userService;
	
	 @PostMapping("/api/v1/auth/register")
	 public ResponseEntity<Result> registerUser(@RequestBody RegisterDto registerUser)
	 {
		 Result  newUser=this.userService.registerUser(registerUser);
		 return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
	 }
	 
	 @GetMapping("/api/v1/users")
	 public ResponseEntity<Result> getAllUsers()
	 {
		 Result allUsers=this.userService.getAllUsers();
		 return ResponseEntity.status(HttpStatus.OK).body(allUsers);
	 }
	 
	 @GetMapping("/api/v1/users/{id}")
	 public ResponseEntity<Result> getUserById(@PathVariable Integer id)
	 {
		 Result user=this.userService.getUserById(id);
		 return ResponseEntity.status(HttpStatus.OK).body(user);
	 }
	 
	 
}
