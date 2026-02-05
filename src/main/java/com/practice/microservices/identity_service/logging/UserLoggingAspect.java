package com.practice.microservices.identity_service.logging;
	

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.practice.microservices.identity_service.dtos.UserDto;

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
				if(arg instanceof UserDto req)
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
		
		@AfterReturning("execution(* com.practice.microservices.identity_service.controllers.UserController.updateUser(..))")
		public void  updateLogSuccess(JoinPoint joinPoint)
		{
			Object[] args=joinPoint.getArgs();
			logger.info("Update Method Called for user with id {} and email {}",args[0],args[1]);
		}
		
		@AfterThrowing(pointcut="execution(* com.practice.microservices.identity_service.controllers.UserController.updateUser(..))"
		 , throwing="catchException")
		public void updateLogFailure(Exception catchException)
		{
			  logger.error("User registration failed | reason={}",
					  catchException.getMessage());
		}
		
	}