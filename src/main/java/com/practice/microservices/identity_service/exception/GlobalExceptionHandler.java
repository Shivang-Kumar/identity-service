package com.practice.microservices.identity_service.exception;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.practice.microservices.identity_service.utility.Result;

@RestControllerAdvice
public class GlobalExceptionHandler {

	
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Result> handleArgumentInvalidException(MethodArgumentNotValidException ex)
	{
		List<ObjectError> allErrors=ex.getBindingResult().getAllErrors();
		Map<String,String> hmap=new HashMap<>(allErrors.size());
		allErrors.forEach((error)->{
			String key=((FieldError) error).getField();
			String value=error.getDefaultMessage();
			hmap.put(key, value);
		});
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Result(false,"Please correct the input and try again",hmap));
		
	}
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<Result> databaseException(RuntimeException ex)
	{
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Result(false,ex.getMessage(),null));
	}
	
	
	
}
