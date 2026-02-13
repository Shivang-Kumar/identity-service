package com.practice.microservices.identity_service.security;


import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.InvalidKeyException;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {
	
	private static final String secret="My-super-hard-to-crack-secret-no-one-can-even-think-about-it-Its-that-hard";
	
	private static final SecretKey key=Keys.hmacShaKeyFor(secret.getBytes());
	
	public static final String generateToken(String subject) throws InvalidKeyException
	{
		return Jwts.builder()
				.setSubject(subject)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+1000*60*60*10))
				.signWith(key,SignatureAlgorithm.HS256)
				.compact();
	}
	
	public static String extractUsername(String token) {
		return extractClaims(token).getSubject();
	}
	
	
	private static Claims extractClaims(String token)
	{	
		return Jwts.parser().setSigningKey(key).build().parseClaimsJws(token).getBody();
		
	}
	
	public boolean validateToken(String username,UserDetails userDetails,String token)
	{
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
	
	private static boolean isTokenExpired(String token)
	{
		return extractClaims(token).getExpiration().before(new Date());
	}

}
