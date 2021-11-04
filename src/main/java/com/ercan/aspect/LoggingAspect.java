package com.ercan.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Pointcut("execution(* com.ercan.SpringDataJpaApplication.main(..))")
    public void main() {
    }

    @Around("execution(* com.ercan.controller.*.*(..))")
    public Object logAllMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        if (log.isDebugEnabled()){
            log.debug("Enter : {}.{}() with argument[s] = {} ",joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
        }
        try {
            Object result = joinPoint.proceed();
            if (log.isDebugEnabled()) {
                log.debug("Exit: {}.{}() with result = {}", joinPoint.getSignature().getDeclaringTypeName(),
                        joinPoint.getSignature().getName(), result);
            }

            return result;
        }catch (IllegalArgumentException e){
            log.error("Illegal argument: {} in {}.{}()", Arrays.toString(joinPoint.getArgs()),
                    joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
            throw e;
        }
    }


    @AfterThrowing(value = "execution(* com.ercan.controller.TestController.*(..))", throwing = "ex")
    public void logAfterThrowing(JoinPoint joinPoint, Exception ex) {
        log.error("Target Method resulted into exception, message {} ", ex.getMessage());

    }


}
