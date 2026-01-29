package com.practice.microservices.identity_service.convertor;

import java.time.Instant;

import org.springframework.stereotype.Component;

import com.practice.microservices.identity_service.dtos.UserDto;
import com.practice.microservices.identity_service.entity.AccountStatus;
import com.practice.microservices.identity_service.entity.UserEntity;

@Component
public class UserDtoToUserConverter {
	
	public UserEntity convertRegisterDtoToUser(UserDto registerDto)
	{
		UserEntity user=new UserEntity(null, registerDto.email(), registerDto.password(),registerDto.firstName(),registerDto.lastName(), registerDto.phone(), registerDto.role(),AccountStatus.DISABLED,false, Instant.now(), Instant.now(),Instant.now());
		return user;
	}

}
