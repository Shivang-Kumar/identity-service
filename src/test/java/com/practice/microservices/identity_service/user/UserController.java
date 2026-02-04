package com.practice.microservices.identity_service.user;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.microservices.identity_service.dtos.UserDto;
import com.practice.microservices.identity_service.entity.AccountStatus;
import com.practice.microservices.identity_service.entity.Role;
import com.practice.microservices.identity_service.entity.UserEntity;
import com.practice.microservices.identity_service.services.UserService;
import com.practice.microservices.identity_service.utility.Result;


@SpringBootTest
@AutoConfigureMockMvc(addFilters=false)
public class UserController {
	
	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	UserService userService;
	
	ObjectMapper objectMapper=new ObjectMapper();
	
	
	
	@Test
	void registerUserSuccess() throws Exception
	{
		UserDto userDto=new UserDto("abc@gmail.com","myPassword","shiv","kumar",78405014,Role.CUSTOMER);
		String json=this.objectMapper.writeValueAsString(userDto);
		UserEntity user=new UserEntity(null, "abc@gmail.com","myPassword","shiv","kumar",78405014,Role.CUSTOMER,AccountStatus.DISABLED,false, Instant.now(), Instant.now(),Instant.now());
		
		given(this.userService.registerUser(Mockito.any(UserDto.class))).willReturn(new Result(true,"Account Created Successfully",user));

		
		this.mockMvc.perform(post("/api/v1/auth/register").contentType(MediaType.APPLICATION_JSON).content(json)
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.flag").value("true"))
		.andExpect(jsonPath("$.message").value("Account Created Successfully"));

	}
	
	@Test
	void registerUser_should_return500_WhenServiceFails() throws Exception
	{
		UserDto userDto=new UserDto("abc@gmail.com","myPassword","shiv","kumar",78405014,Role.CUSTOMER);
		String json=this.objectMapper.writeValueAsString(userDto);
		
		when(this.userService.registerUser(any(UserDto.class))).thenThrow(new RuntimeException("Could not save user to database, please try agin after sometime!"));
	
		this.mockMvc.perform(post("/api/v1/auth/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.flag").value("false"))
		.andExpect(jsonPath("$.message").value("\"Could not save user to database, please try agin after sometime!"));
	}
}
