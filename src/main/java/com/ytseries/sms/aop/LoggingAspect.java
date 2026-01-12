package com.ytseries.sms.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    private static final DateTimeFormatter FORMATER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    int reqId = ThreadLocalRandom.current().nextInt(1, 10000000);

//  pointCut is for executing for all the methods in service package and it classes
    @Pointcut("execution(* com.ytseries.sms.services..*(..))")
    public void serviceLogging() {}

    @Around("serviceLogging()")
    public Object loggingMethodExecution (ProceedingJoinPoint pjp) throws Throwable {
//       Get Service Class Name
        String serviceName = pjp.getTarget().getClass().getSimpleName();

//       Get Method Name
        String methodName = pjp.getSignature().getName();

        LocalDateTime startTime = LocalDateTime.now();
        String startTimerStr = startTime.format(FORMATER);

//       Entry Log
        log.info("=============Start ReqId {} ==============", reqId);
        log.info("Service: {}, Method: {}", serviceName, methodName);
        log.info("startTimer: {}ms", startTimerStr);
        log.info("============================");

        Object result = null;
        Throwable exception = null;

        try {
            result = pjp.proceed();
            return result;
        } catch (Throwable throwable1) {
            exception = throwable1;
            throw exception;
        } finally {
//           Method End Logging
            LocalDateTime endTime = LocalDateTime.now();
            String endTimerStr = endTime.format(FORMATER);

            Duration duration = Duration.between(startTime, endTime);
            long durationsMillis = duration.toMillis();

            log.info("============ END for ReqId {} ================", reqId);
            log.info("Service: {}, Method: {}", serviceName, methodName);
            if(exception != null) {
                log.error("Status: Failed | exception: {}", exception.getMessage());
            }
            log.info("endTimer: {}", endTimerStr);
            log.info("executionTIme: {}", durationsMillis);
            log.info("============================");

        }
    }
}
