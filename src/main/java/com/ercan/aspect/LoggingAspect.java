package com.ercan.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Pointcut("execution(* com.ercan.SpringDataJpaApplication.main(..))")
    public void main() {
    }

    @After("main()")
    public void logMainMethod(ProceedingJoinPoint joinPoint) {
        log.info("SPRING BOOT PROJECT STARTED :)");
    }

    @AfterThrowing(value = "execution(* com.ercan.controller.TestController.*(..))", throwing = "ex")
    public void logAfterThrowing(JoinPoint joinPoint, Exception ex) {
        log.error("Target Method resulted into exception, message {} ", ex.getMessage());

    }

}
