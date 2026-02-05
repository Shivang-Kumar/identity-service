package com.practice.microservices.identity_service.services;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.practice.microservices.identity_service.convertor.UserDtoToUserConverter;
import com.practice.microservices.identity_service.dtos.UserDto;
import com.practice.microservices.identity_service.entity.UserEntity;
import com.practice.microservices.identity_service.repositories.UserRepository;
import com.practice.microservices.identity_service.utility.Result;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
		
	UserRepository userRepository;
	UserDtoToUserConverter userConverter;
	PasswordEncoder passwordEncoder;

	@Override
	public Result registerUser(UserDto registerDto) {
	
		try {
		UserEntity user=userConverter.convertRegisterDtoToUser(registerDto);
		user.setPasswordHash(passwordEncoder.encode(registerDto.password()));
		UserEntity savedUser=userRepository.save(user);
		return new Result(true,"Account Created Successfully",savedUser);
		}
		catch(Exception e)
		{
			System.out.println(e);
			throw new RuntimeException("Could not save user to database, please try again after sometime!");
		}
	}

	@Override
	public Result getAllUsers() {
		List<UserEntity> allUsers=userRepository.findAll();
		return new Result(true,"All Users Registered",allUsers);
	}

	@Override
	public Result getUserById(UUID id) {
		Optional<UserEntity> user=userRepository.findById(id);
		return  new Result(true,"Found User By Id",user.get());
	}

	@Override
	public Result updateUserById(UUID userId,UserEntity updatedUser) {
		
		UserEntity savedUser= userRepository.findById(userId).map(oldUser -> {
			oldUser.setUpdatedAt(Instant.now());
			oldUser.setPhone(updatedUser.getPhone());
			oldUser.setFirstName(updatedUser.getFirstName());
			oldUser.setLastName(updatedUser.getLastName());
			
			return this.userRepository.save(oldUser);
		}).orElseThrow(() -> new RuntimeException("Cannot find user with this UUID"));
		
		return new Result(true,"User Updated Successfully",savedUser);
	}
	
	
	
}
