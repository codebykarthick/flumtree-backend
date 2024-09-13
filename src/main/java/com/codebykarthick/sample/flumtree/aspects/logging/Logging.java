package com.codebykarthick.sample.flumtree.aspects.logging;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Log4j2
public class Logging {
  /**
   * Just to demonstrate Spring's AOP capabilities. This captures events just before entering any
   * controller.
   *
   * @param joinPoint JoinPoint provided by the aspect at the event time.
   */
  @Before(value = "execution(* com.codebykarthick.sample.flumtree.controllers..*(..))")
  public void logControllerRequest(JoinPoint joinPoint) {
    log.info(
        "< Entering {} with args {} >", joinPoint.getSignature().getName(), joinPoint.getArgs());
  }

  /**
   * Runs before and after the execution to capture the duration taken by endpoints to serve.
   *
   * @param joinPoint The joinPoint instance aspect provided by spring
   * @throws Throwable Exception thrown during the execution of method.
   */
  @Around(value = "execution(* com.codebykarthick.sample.flumtree.controllers..*(..))")
  public Object measureControllerPerformance(ProceedingJoinPoint joinPoint) throws Throwable {
    long toc = System.currentTimeMillis();
    // We have to capture and return the response, otherwise dummy 200 OK will be sent instead.
    Object response = joinPoint.proceed();
    long tic = System.currentTimeMillis();
    log.info("{} took {} ms to execute.", joinPoint.getSignature().getName(), (tic - toc));

    return response;
  }

  /**
   * To log what was responded back to user.
   *
   * @param joinPoint JoinPoint instance provided by Spring boot
   * @param returnValue The values returned to user in the response.
   */
  @AfterReturning(
      value = "execution(* com.codebykarthick.sample.flumtree.controllers..*(..))",
      returning = "returnValue")
  public void logControllerResponse(JoinPoint joinPoint, Object returnValue) {
    log.info("< Leaving {} with response {} >", joinPoint.getSignature().getName(), returnValue);
  }
}
