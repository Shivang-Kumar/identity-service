package com.practice.microservices.identity_service.services;

import com.practice.microservices.identity_service.dtos.RegisterDto;
import com.practice.microservices.identity_service.utility.Result;

public interface UserService {

	Result registerUser(RegisterDto register);

}
