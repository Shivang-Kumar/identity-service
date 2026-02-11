package com.practice.microservices.identity_service.dtos;

import com.practice.microservices.identity_service.dtos.marker.AuthRequest;
import com.practice.microservices.identity_service.entity.Role;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record UserDto(

		@Email(message="Email Should be of valid format")
		@NotNull(message="Email is required" , groups= {AuthRequest.class})
		String email,
		@NotNull(message="Password is required",groups= {AuthRequest.class})
		String password,
		@NotNull(message="First Name is required")
		String firstName,
		@NotNull(message="Last Name is required")
		String lastName, 
		@NotNull(message="Phone number is required")
		Integer phone,
		@Enumerated(EnumType.STRING) Role role) {

}
