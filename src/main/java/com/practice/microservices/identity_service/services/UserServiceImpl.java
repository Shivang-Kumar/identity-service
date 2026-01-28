package com.practice.microservices.identity_service.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.practice.microservices.identity_service.convertor.RegisterDtoToUserConverter;
import com.practice.microservices.identity_service.dtos.RegisterDto;
import com.practice.microservices.identity_service.entity.UserEntity;
import com.practice.microservices.identity_service.repositories.UserRepository;
import com.practice.microservices.identity_service.utility.Result;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
		
	UserRepository userRepository;
	RegisterDtoToUserConverter userConverter;

	@Override
	public Result registerUser(RegisterDto registerDto) {
	
		try {
		UserEntity user=userConverter.convertRegisterDtoToUser(registerDto);
		UserEntity savedUser=userRepository.save(user);
		return new Result(true,"Account Created Successfully",savedUser);
		}
		catch(Exception e)
		{
			throw new RuntimeException("Could not save user to database, please try agin after sometime!");
		}
	}

	@Override
	public Result getAllUsers() {
		List<UserEntity> allUsers=userRepository.findAll();
		return new Result(true,"All Users Registered",allUsers);
	}

	@Override
	public Result getUserById(Integer id) {
		Optional<UserEntity> user=userRepository.findById(id);
		return  new Result(true,"Found User By Id",user.get());
	}
	
	
	
}
