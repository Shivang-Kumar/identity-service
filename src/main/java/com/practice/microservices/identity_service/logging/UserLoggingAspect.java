package com.practice.microservices.identity_service.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.practice.microservices.identity_service.dtos.UserDto;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class UserLoggingAspect {

    @Around("execution(* com.practice.microservices.identity_service.controllers.*.*(..))")
    public Object logApiCall(ProceedingJoinPoint joinPoint) throws Throwable {

        long startTime = System.currentTimeMillis();

        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                        .getRequest();

        String httpMethod = request.getMethod();
        String uri = request.getRequestURI();
        String methodName = joinPoint.getSignature().toShortString();

        log.info("Incoming request → {} {} | method={}",
                httpMethod,
                uri,
                methodName
        );

        // Log DTOs safely
        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof UserDto req) {
                log.info("Request payload → username={} email={}",
                        req.firstName(),
                        req.email()
                );
            }
        }

        try {
            Object result = joinPoint.proceed();

            long timeTaken = System.currentTimeMillis() - startTime;

            log.info("Request completed → {} {} | timeTaken={}ms",
                    httpMethod,
                    uri,
                    timeTaken
            );

            return result;

        } catch (Exception ex) {

            long timeTaken = System.currentTimeMillis() - startTime;

            log.error("Request failed → {} {} | timeTaken={}ms | error={}",
                    httpMethod,
                    uri,
                    timeTaken,
                    ex.getMessage()
            );

            throw ex;
        }
    }
}