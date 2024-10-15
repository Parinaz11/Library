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

    @AfterReturning(pointcut = "execution(* com.api.Library.service.*.*(..))", returning = "result")
    public void logAfterSuccessfulExecution(Object result) {
        logger.info("Method executed successfully, result: {}", result);
    }

//    // Example of logging a custom message using method parameters
//    @AfterReturning(pointcut = "execution(* com.api.Library.service.BookService.addBook(..)) && args(bookName, ..)")
//    public void logBookAdded(String bookName) {
//        logger.info("Book '{}' added successfully!", bookName);
//    }

//    @AfterReturning(pointcut = "execution(* com.api.Library.service.UserService.(..)) && args(userName, ..)")
//    public void logUserAdded(String userName) {
//        logger.info("User '{}' added successfully!", userName);
//    }

}
