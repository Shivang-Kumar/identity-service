 	package com.practice.microservices.identity_service.controllers;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.practice.microservices.identity_service.dtos.UserDto;
import com.practice.microservices.identity_service.dtos.marker.AuthRequest;
import com.practice.microservices.identity_service.entity.UserEntity;
import com.practice.microservices.identity_service.security.JwtUtils;
import com.practice.microservices.identity_service.services.UserService;
import com.practice.microservices.identity_service.utility.Result;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class UserController {
	
	private final UserService userService;
	private final AuthenticationManager authenticationManager;
	
	 @PostMapping("/api/v1/auth/register")
	 public ResponseEntity<Result> registerUser(@Validated @RequestBody UserDto registerUser)
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
	 public ResponseEntity<Result> getUserById(@PathVariable("id") UUID userId)
	 {
		 Result user=this.userService.getUserById(userId);
		 return ResponseEntity.status(HttpStatus.OK).body(user);
	 }
	 
	 @PutMapping("/api/v1/users/{id}")
	 public ResponseEntity<Result> updateUserById(@PathVariable("id") UUID userId,@RequestBody UserDto updateUser)
	 {
		 Result user=this.userService.updateUserById(userId,updateUser);
		 return  ResponseEntity.status(HttpStatus.OK).body(user);
	 }
	 
	 
	 @PostMapping("/api/v1/login")
	 public ResponseEntity<Result> generateToken(@Validated(AuthRequest.class) @RequestBody UserDto authRequest)
	 {
		 authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(authRequest.email(),authRequest.password())
				 );
		String jwtToken=JwtUtils.generateToken(authRequest.email());
		return ResponseEntity.status(HttpStatus.OK).body(new Result(true,"JWT Token Created Successfully",jwtToken));
	 }
	 
	 
	 
}
