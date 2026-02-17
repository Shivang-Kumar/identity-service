package com.practice.microservices.identity_service.services;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.practice.microservices.identity_service.convertor.UserDtoToUserConverter;
import com.practice.microservices.identity_service.dtos.UserDto;
import com.practice.microservices.identity_service.entity.UserEntity;
import com.practice.microservices.identity_service.repositories.UserRepository;
import com.practice.microservices.identity_service.security.JwtUtils;
import com.practice.microservices.identity_service.utility.Result;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
		
	UserRepository userRepository;
	UserDtoToUserConverter userConverter;
	PasswordEncoder passwordEncoder;
	RedisCacheClient redisTemplate;
	JwtUtils jwtUtils;
 
	@Override
	public Result registerUser(UserDto registerDto) {
	
		try {
		userRepository.findByEmail(registerDto.email()).ifPresent(user -> {
			throw new RuntimeException("User with this email already exits");
		});
		UserEntity user=userConverter.convertRegisterDtoToUser(registerDto);
		user.setPassword(passwordEncoder.encode(registerDto.password()));
		UserEntity savedUser=userRepository.save(user);
		return new Result(true,"Account Created Successfully",savedUser);
		}
		catch(RuntimeException e)
		{
			throw e;
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
	public Result updateUserById(UUID userId,UserDto updatedUser) {
		
		UserEntity savedUser= userRepository.findById(userId).map(oldUser -> {
			oldUser.setUpdatedAt(Instant.now());
			oldUser.setPhone(updatedUser.phone());
			oldUser.setFirstName(updatedUser.firstName());
			oldUser.setLastName(updatedUser.lastName());
			
			return this.userRepository.save(oldUser);
		}).orElseThrow(() -> new RuntimeException("Cannot find user with this UUID"));
		
		return new Result(true,"User Updated Successfully",savedUser);
	}
//
//	@Override
//	public void invalidateToken(HttpServletRequest request) {
//		String authHeader=request.getHeader("Authorization");
//		if(authHeader==null||authHeader.startsWith("Bearer ")==false)
//		{
//			throw new RuntimeException("Bad Request JWT Token is required");
//		}
//		
//		String token=authHeader.substring(7);
//		String timeLeft=this.jwtUtils.getTimeLeft(token);
//		redisTemplate.set("Blacklisted:"+token, "true" , timeLeft, TimeUnit.MILLISECONDS);
//		
//		
//	}
	
	
	
}
