package com.challenge.backend_challenge.aspect;

import com.challenge.backend_challenge.service.CallHistoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
@RequiredArgsConstructor
public class ControllerAspect {

  private final CallHistoryService callHistoryService;
  private final ObjectMapper objectMapper;

  @Pointcut("execution(* com.challenge.backend_challenge.controller.*.*(..))")
  public void controllerMethods() {}

  @AfterReturning(pointcut = "controllerMethods()", returning = "result")
  public void logAfterReturning(JoinPoint joinPoint, Object result) {
    try {
      HttpServletRequest request =
          ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

      String endpoint = request.getRequestURI();
      String parameters = objectMapper.writeValueAsString(joinPoint.getArgs());
      String response = objectMapper.writeValueAsString(result);

      callHistoryService.saveCallAsync(endpoint, parameters, response, false);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @AfterThrowing(pointcut = "controllerMethods()", throwing = "exception")
  public void logAfterThrowing(JoinPoint joinPoint, Exception exception) {
    try {
      HttpServletRequest request =
          ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

      String endpoint = request.getRequestURI();
      String parameters = objectMapper.writeValueAsString(joinPoint.getArgs());
      String response = exception.getMessage();

      callHistoryService.saveCallAsync(endpoint, parameters, response, true);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
