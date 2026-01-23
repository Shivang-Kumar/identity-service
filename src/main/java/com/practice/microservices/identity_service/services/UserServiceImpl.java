package com.practice.microservices.identity_service.services;

import org.springframework.stereotype.Service;

import com.practice.microservices.identity_service.dtos.RegisterDto;
import com.practice.microservices.identity_service.repositories.UserRepository;
import com.practice.microservices.identity_service.utility.Result;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
		
	UserRepository userRepository;

	@Override
	public Result registerUser(RegisterDto register) {
		
		//Write logic to save user to database.
		return null;
	}
	
	
	
}
