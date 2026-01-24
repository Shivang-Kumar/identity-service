package com.practice.microservices.identity_service.user;

import java.time.Instant;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.practice.microservices.identity_service.convertor.RegisterDtoToUserConverter;
import com.practice.microservices.identity_service.dtos.RegisterDto;
import com.practice.microservices.identity_service.entity.AccountStatus;
import com.practice.microservices.identity_service.entity.Role;
import com.practice.microservices.identity_service.entity.UserEntity;
import com.practice.microservices.identity_service.repositories.UserRepository;
import com.practice.microservices.identity_service.services.UserServiceImpl;
import com.practice.microservices.identity_service.utility.Result;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
	
	@Mock
	UserRepository userRepository;
	
	@Mock
	RegisterDtoToUserConverter userConverter;
	
	@InjectMocks
	UserServiceImpl userService;
	
	@Test
	void registerUser_shouldSaveUserAndReturnSuccessResult()
	{
		RegisterDto registerDto=new RegisterDto("myemail414@gmail.com","mypassword", "firstname", "lastname", 1549080807 , Role.CUSTOMER);
		UserEntity user=new UserEntity(null, registerDto.email(), registerDto.passwordHash(),registerDto.firstName(),registerDto.lastName(), registerDto.phone(), registerDto.role(),AccountStatus.DISABLED,false, Instant.now(), Instant.now(),Instant.now());
		
		//given 
		given(this.userConverter.convertRegisterDtoToUser(registerDto)).willReturn(user);
		given(this.userRepository.save(user)).willReturn(user);
		//when
		Result result=userService.registerUser(registerDto);
		//then
		assertThat(result.getMessage()).isEqualTo("Account Created Successfully");
		assertThat(((UserEntity)result.getData()).getEmail()).isEqualTo("myemail414@gmail.com");
		verify(userRepository,times(1)).save(user);
		verify(userConverter,times(1)).convertRegisterDtoToUser(registerDto);
	}
	
	
	@Test
	void registerUser_shouldThrowError()
	{
		RegisterDto registerDto=new RegisterDto("myemail414@gmail.com","mypassword", "firstname", "lastname", 1549080807 , Role.CUSTOMER);
		UserEntity user=new UserEntity(null, registerDto.email(), registerDto.passwordHash(),registerDto.firstName(),registerDto.lastName(), registerDto.phone(), registerDto.role(),AccountStatus.DISABLED,false, Instant.now(), Instant.now(),Instant.now());
		
		//given 
		given(this.userConverter.convertRegisterDtoToUser(registerDto)).willReturn(user);
		given(this.userRepository.save(user)).willThrow(new RuntimeException());
		
		
		// when and then
		assertThrows(RuntimeException.class,() -> userService.registerUser(registerDto));
		verify(userRepository,times(1)).save(user);
		verify(userConverter,times(1)).convertRegisterDtoToUser(registerDto);
	}

}
