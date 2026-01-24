package com.practice.microservices.identity_service.dtos;

import com.practice.microservices.identity_service.entity.Role;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public record RegisterDto(

		String email, String passwordHash, String firstName, String lastName, Integer phone,
		@Enumerated(EnumType.STRING) Role role) {

}
