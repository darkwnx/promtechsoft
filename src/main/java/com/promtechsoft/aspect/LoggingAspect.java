package com.promtechsoft.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Pointcut("execution(* com.promtechsoft.controller.*.*(..))")
    public void controllerMethods() {}

    @Pointcut("execution(* com.promtechsoft.service.*.*(..))")
    public void serviceMethods() {}

    @Around("controllerMethods()")
    public Object logControllerMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        Instant start = Instant.now();

        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        log.info("▶ Контроллер: {}.{} | Аргументы: {}",
                joinPoint.getTarget().getClass().getSimpleName(),
                methodName,
                args.length > 0 ? args : "нет");

        try {
            Object result = joinPoint.proceed();
            long duration = Duration.between(start, Instant.now()).toMillis();
            log.info("✓ Контроллер: {} выполнен за {}ms", methodName, duration);
            return result;
        } catch (Exception e) {
            log.error("✗ Контроллер: {} завершился с ошибкой: {}", methodName, e.getMessage());
            throw e;
        }
    }

    @Around("serviceMethods()")
    public Object logServiceMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        Instant start = Instant.now();

        String methodName = joinPoint.getSignature().getName();

        log.debug("▶ Сервис: {}.{}",
                joinPoint.getTarget().getClass().getSimpleName(),
                methodName);

        try {
            Object result = joinPoint.proceed();
            long duration = Duration.between(start, Instant.now()).toMillis();
            log.debug("✓ Сервис: {} выполнен за {}ms", methodName, duration);
            return result;
        } catch (Exception e) {
            log.error("✗ Сервис: {} ошибка: {}", methodName, e.getMessage());
            throw e;
        }
    }
}