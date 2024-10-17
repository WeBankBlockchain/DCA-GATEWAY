package com.webank.blockchain.dca.gateway.aspect;

import com.google.common.util.concurrent.RateLimiter;
import com.webank.blockchain.dca.gateway.enums.ResponseEnum;
import com.webank.blockchain.dca.gateway.vo.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.HashMap;

@Aspect
@Component
@Slf4j
public class RateLimitAspect {
    @Autowired private HashMap<String, RateLimiter> rateLimiterMap;

    @Pointcut("@annotation(com.webank.blockchain.dca.gateway.aspect.Limit)")
    public void serviceLimit() {}

    @Around("serviceLimit()")
    public Object doAround(ProceedingJoinPoint point) throws Throwable {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Object target = point.getTarget();
        Method method =
                target.getClass().getMethod(signature.getName(), signature.getParameterTypes());
        Limit annotation = method.getAnnotation(Limit.class);
        String functionName = annotation.name();
        RateLimiter rateLimiter = rateLimiterMap.get(functionName);
        if (rateLimiter.tryAcquire()) {
            log.info("RateLimiter: function access normal, functionName: {}", annotation.name());
            return point.proceed();
        } else {
            log.error(
                    "onError: RateLimiter function is busy now, functionName: {}",
                    annotation.name());
            if(method.getName().equals("addWatermark")){
                throw new RuntimeException(ResponseEnum.Request_Rate_Limit.getResponseCode());
            }
            return CommonResponse.error(ResponseEnum.Request_Rate_Limit);
        }
    }
}
