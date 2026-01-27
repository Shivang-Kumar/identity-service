package com.practice.microservices.identity_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.practice.microservices.identity_service.utility.Result;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<Result> databaseException(RuntimeException ex)
	{
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Result(false,ex.getMessage(),null));
	}
	
	
}
