package com.practice.microservices.identity_service.user;

import java.time.Instant;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.practice.microservices.identity_service.convertor.UserDtoToUserConverter;
import com.practice.microservices.identity_service.dtos.UserDto;
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
	UserDtoToUserConverter userConverter;
	
	@InjectMocks
	UserServiceImpl userService;
	
	@Mock
	PasswordEncoder passwordEncoder;
	
	@Test
	void registerUser_shouldSaveUserAndReturnSuccessResult()
	{
		UserDto registerDto=new UserDto("myemail414@gmail.com","mypassword", "firstname", "lastname", 1549080807 , Role.CUSTOMER);
		UserEntity user=new UserEntity(null, registerDto.email(), registerDto.password(),registerDto.firstName(),registerDto.lastName(), registerDto.phone(), registerDto.role(),AccountStatus.DISABLED,false, Instant.now(), Instant.now(),Instant.now());
		
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
		UserDto registerDto=new UserDto("myemail414@gmail.com","mypassword", "firstname", "lastname", 1549080807 , Role.CUSTOMER);
		UserEntity user=new UserEntity(null, registerDto.email(), registerDto.password(),registerDto.firstName(),registerDto.lastName(), registerDto.phone(), registerDto.role(),AccountStatus.DISABLED,false, Instant.now(), Instant.now(),Instant.now());
		
		//given 
		given(this.userConverter.convertRegisterDtoToUser(registerDto)).willReturn(user);
		given(this.userRepository.save(user)).willThrow(new RuntimeException());
		given(this.passwordEncoder.encode(registerDto.password())).willReturn("%HS&DH");
		
		
		// when and then
		assertThrows(RuntimeException.class,() -> userService.registerUser(registerDto));
		verify(userRepository,times(1)).save(user);
		verify(userConverter,times(1)).convertRegisterDtoToUser(registerDto);
	}

}
