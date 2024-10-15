package com.api.Library.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class LoggingAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @After(value = "execution(* com.api.Library.service.UserService.*(..))")  //Pointcut
    public void after(JoinPoint joinPoint) //Advice
    {
        logger.info("After execution of {}", joinPoint);
    }

    @Before("execution(* com.api.Library.service.*.*(..))")  //Pointcut
    public void before(JoinPoint joinPoint) //Advice
    {
        logger.info("Before execution of {}", joinPoint);
    }

    @Around("execution(* com.api.Library.service.*.*(..))")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("Befor execution of {}", joinPoint);
        Object result = joinPoint.proceed();
        logger.info("After execution of {}", joinPoint);
        return result;
    }


    @AfterThrowing(pointcut = "execution(* com.api.Library.service.*.*(..))", throwing = "ex")
    public void logAfterThrowing(Exception ex) {
        logger.error("An Exception has been throws: " + ex.getMessage(), ex);
    }

    @AfterReturning(
            pointcut="execution(* com.api.Library.service.*.*(..))",
            returning="retVal")
    public void doAccessCheck(Object retVal) {
    }

}
