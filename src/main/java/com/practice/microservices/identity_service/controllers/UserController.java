package com.practice.microservices.identity_service.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
		 try {
		 Result  newUser=this.userService.registerUser(registerUser);
		 return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
		 }
		 catch(Exception e){
			 
			 //Ideally should be handled by the Exception Handler classes
			 return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Result(false,"Could not register user please try gain after sometime!",null));
		 }
	 }
}
