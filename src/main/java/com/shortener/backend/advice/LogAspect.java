package com.shortener.backend.advice;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author M.dehghan
 * @since 2020-09-03
 */
@Aspect
@Component
public class LogAspect {
    @Around("execution(public * com.shortener.backend.controller.ServiceController.*(..))")
    public Object serviceLog(ProceedingJoinPoint joinPoint) throws Throwable {
        Class joinPointLocation = joinPoint.getSourceLocation().getWithinType();
        Logger logger = LoggerFactory.getLogger(joinPointLocation);
        String methodName = joinPoint.getSignature().getName();
        Object[] methodParams = joinPoint.getArgs();
        for (Object methodParam : methodParams) {
            logger.info("{} Request:\n {}", methodName, methodParam.toString());
        }
        Object object = joinPoint.proceed(methodParams);
        logger.info("{} Response:\n {}", methodName, object.toString());
        return object;
    }
}
