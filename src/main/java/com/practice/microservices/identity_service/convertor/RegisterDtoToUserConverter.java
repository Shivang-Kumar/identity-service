package com.practice.microservices.identity_service.convertor;

import java.time.Instant;

import org.springframework.stereotype.Component;

import com.practice.microservices.identity_service.dtos.RegisterDto;
import com.practice.microservices.identity_service.entity.AccountStatus;
import com.practice.microservices.identity_service.entity.UserEntity;

@Component
public class RegisterDtoToUserConverter {
	
	public UserEntity convertRegisterDtoToUser(RegisterDto registerDto)
	{
		UserEntity user=new UserEntity(null, registerDto.email(), registerDto.passwordHash(),registerDto.firstName(),registerDto.lastName(), registerDto.phone(), registerDto.role(),AccountStatus.DISABLED,false, Instant.now(), Instant.now(),Instant.now());
		return user;
	}

}
