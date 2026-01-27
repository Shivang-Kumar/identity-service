package com.practice.microservices.identity_service.logging;
	

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
	import org.slf4j.Logger;
	import org.slf4j.LoggerFactory;
	import org.springframework.stereotype.Component;

import com.practice.microservices.identity_service.dtos.RegisterDto;

	@Aspect
	@Component
	public class UserLoggingAspect {
		
		private static  final Logger logger=LoggerFactory.getLogger(UserLoggingAspect.class);
		
		@Around("execution(* com.practice.microservices.identity_service.controllers.UserController.registerUser(..))")
		public Object registerLog(ProceedingJoinPoint  joinPoint) throws Throwable{
			
			long startTime=System.currentTimeMillis();
			String methodName=joinPoint.getSignature().getName();
			logger.info("API called: {}",methodName);
			
			for(Object arg: joinPoint.getArgs())
			{
				if(arg instanceof RegisterDto req)
				{
					logger.info("Register Request | username={} | email={}",req.firstName(),req.email());
				}
				
			}
			
		  try {
			  Object result=joinPoint.proceed();
			  long timeTaken=System.currentTimeMillis()-startTime;
			  logger.info("API Suceess:{} | time:{}ms",methodName, timeTaken);
			  return result;
		  }
		  catch (Exception ex)
		  {
			  logger.error("API failed: {} | reason={}",methodName,ex.getMessage(),ex);
			  throw ex;
		  }
			
		}

	}