package com.practice.microservices.identity_service.services;

import java.util.UUID;

import com.practice.microservices.identity_service.dtos.UserDto;
import com.practice.microservices.identity_service.utility.Result;

public interface UserService {

	Result registerUser(UserDto register);

	Result getAllUsers();


	Result getUserById(UUID id);

}
