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

    // Example of logging a custom message using method parameters
    @AfterReturning(pointcut = "execution(* com.api.Library.service.BookService.addBook(..)) && args(bookName, ..)")
    public void logBookAdded(String bookName) {
        logger.info("Book '{}' added successfully!", bookName);
    }

    // Example of logging a custom message using method parameters
    @AfterReturning(pointcut = "execution(* com.api.Library.service.BookService.deleteBook(..)) && args(bookName, ..)")
    public void logBookRemoved(String bookName) {
        logger.info("Book '{}' removed successfully!", bookName);
    }

    // Example of logging a custom message using method parameters
    @AfterReturning(pointcut = "execution(* com.api.Library.service.BookService.deleteBook(..)) && args(bookName, ..)")
    public void logBookUpdated(String bookName) {
        logger.info("Book '{}' updated successfully!", bookName);
    }


    @AfterReturning(pointcut = "execution(* com.api.Library.service.UserService.saveUser(..)) && args(userName, ..)")
    public void logUserAdded(String userName) {
        logger.info("User '{}' added successfully!", userName);
    }

    @AfterReturning(pointcut = "execution(* com.api.Library.service.UserService.saveUser(..)) && args(userName, ..)")
    public void logUserUpdated(String userName) {
        logger.info("User '{}' updated successfully!", userName);
    }

    @AfterReturning(pointcut = "execution(* com.api.Library.service.UserService.saveUser(..)) && args(userName, ..)")
    public void logUserRemoved(String userName) {
        logger.info("User '{}' removed successfully!", userName);
    }

    @AfterReturning(pointcut = "execution(* com.api.Library.service.ReservationService.addReservation(..)) && args(reservationName, ..)")
    public void logReservationAdded(String reservationName) {
        logger.info("Reservation '{}' added successfully!", reservationName);
    }

    @AfterReturning(pointcut = "execution(* com.api.Library.service.ReservationService.updateReservation(..)) && args(reservationName, ..)")
    public void logReservationUpdated(String reservationName) {
        logger.info("Reservation '{}' updated successfully!", reservationName);
    }

    @AfterReturning(pointcut = "execution(* com.api.Library.service.ReservationService.removeReservation(..)) && args(reservationName, ..)")
    public void logReservationRemoved(String reservationName) {
        logger.info("Reservation '{}' removed successfully!", reservationName);
    }

    @AfterReturning(pointcut = "execution(* com.api.Library.service.ReservationService.reserve(..))")
    public void logReserve() {
        logger.info("Reservation request successful");
    }


}
