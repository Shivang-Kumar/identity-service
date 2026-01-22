package com.practice.microservices.identity_service.entity;

import java.time.Instant;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@RequiredArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class UserEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	String id;	
	
	@Email
	@NotNull
	String email;	
	
	
	String passwordHash;
	String firstName;
	String lastName;
	Integer phone;
	
	@Enumerated(EnumType.STRING)
	Role role;  // (CUSTOMER, ADMIN)
	@Enumerated(EnumType.STRING)
	AccountStatus status; //Account status (ACTIVE, SUSPENDED)
	
	
	boolean isEmailVerified; 
	Instant createdAt;
	Instant updatedAt;
	Instant lastLoginAt; 
	
}
