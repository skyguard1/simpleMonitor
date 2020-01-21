package com.skyguard.monitor.business.aspect;

import com.skyguard.monitor.trace.MethodInfo;
import com.skyguard.monitor.trace.Monitor;
import com.skyguard.monitor.util.JsonUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @author : xingrufei
 * create at:  2020-01-21  10:58
 * @description:
 */
@Component
@Aspect
public class MonitorAspect {

    private static final Logger LOG = LoggerFactory.getLogger(MonitorAspect.class);

    @Around(value = "execution(* com.skyguard.monitor.business.controller..*.*(..))")
    public Object invoke(ProceedingJoinPoint joinPoint) {

        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        //1.这里获取到所有的参数值的数组
        Object[] args = joinPoint.getArgs();


        try {
            MethodInfo methodInfo = new MethodInfo().buildServiceName("test").buildClassName(className).buildMethodName(methodName).buildParams(Arrays.asList(args));
            Monitor.start(methodInfo);
            Object returnObj = joinPoint.proceed();
            String result = JsonUtil.toJsonString(returnObj);
            Monitor.end(result);
            return returnObj;
        } catch (Throwable throwable) {
            LOG.error("service invoke error",throwable);
            Monitor.exception(throwable);
            throw new RuntimeException(throwable.getMessage());
        }
    }



}
