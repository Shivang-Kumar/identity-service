package com.practice.microservices.identity_service.entity;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.practice.microservices.identity_service.repositories.UserRepository;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@Component
@AllArgsConstructor
public class CustomUserDetails implements UserDetailsService{

	UserRepository userRepository;
	
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		return 	userRepository.findByEmail(email).map(x -> x).orElseThrow(()-> new RuntimeException("Could not find User with Email: "+email));
	}

}
