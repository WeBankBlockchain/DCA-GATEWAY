package com.webank.blockchain.dca.gateway.monitor;

import com.webank.blockchain.dca.gateway.enums.ResponseEnum;
import com.webank.blockchain.dca.gateway.utils.JsonUtils;
import com.webank.blockchain.dca.gateway.vo.CommonRequest;
import com.webank.blockchain.dca.gateway.vo.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(10)
@Aspect
@Component
@Slf4j
public class WebLogMonitorAspect {

    @Pointcut("execution(public * com.webank.blockchain.dca.gateway.controller.json..*.*(..))")
    public void jsonMonitorLog() {}

    @Pointcut("execution(public * com.webank.blockchain.dca.gateway.controller.multipart..*.*(..))" +
            "&& !execution(public * com.webank.blockchain.dca.gateway.controller.multipart.WmUploadController.*(..)))")
    public void multipartMonitorLog() {}

    @Pointcut("execution(public * com.webank.blockchain.dca.gateway.controller.multipart.WmUploadController.*(..))")
    public void addWatermarkMonitorLog() {}

    @Around(value = "addWatermarkMonitorLog()")
    public <T> Object doWmMultipartAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        String seqNo = (String) proceedingJoinPoint.getArgs()[0];
        try {
            Object result = proceedingJoinPoint.proceed();
            MonitorEntry monitorEntry =
                    new MonitorEntry(
                            proceedingJoinPoint.getSignature().getName(),
                            seqNo,
                            ResponseEnum.Success.getResponseCode(),
                            "success",
                            System.currentTimeMillis() - startTime);
            log.info("[{}]", JsonUtils.toJson(monitorEntry));
            return result;
        } catch (Exception e) {
            ResponseEnum ee = ResponseEnum.parse(e.getMessage());
            MonitorEntry monitorEntry =
                    new MonitorEntry(
                            proceedingJoinPoint.getSignature().getName(),
                            seqNo,
                            ee.getResponseCode(),
                            ee.getMessage(),
                            System.currentTimeMillis() - startTime);
            log.info("[{}]", JsonUtils.toJson(monitorEntry));
            throw e;
        }
    }

    @Around("jsonMonitorLog()")
    public <T> Object doJsonAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        CommonRequest request = (CommonRequest) proceedingJoinPoint.getArgs()[0];
        try {
            CommonResponse<T> result = (CommonResponse<T>) proceedingJoinPoint.proceed();
            MonitorEntry monitorEntry =
                    new MonitorEntry(
                            proceedingJoinPoint.getSignature().getName(),
                            request.getSeqNo(),
                            result.getResponseCode(),
                            result.getResponseMessage(),
                            System.currentTimeMillis() - startTime);
            log.info("[{}]", JsonUtils.toJson(monitorEntry));
            return result;
        } catch (Exception e) {
            ResponseEnum ee = ResponseEnum.parse(e.getMessage());
            MonitorEntry monitorEntry =
                    new MonitorEntry(
                            proceedingJoinPoint.getSignature().getName(),
                            request.getSeqNo(),
                            ee.getResponseCode(),
                            ee.getMessage(),
                            System.currentTimeMillis() - startTime);
            log.info("[{}]", JsonUtils.toJson(monitorEntry));
            throw e;
        }
    }

    @Around(value = "multipartMonitorLog()")
    public <T> Object doMultipartAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        String seqNo = (String) proceedingJoinPoint.getArgs()[0];
        try {
            CommonResponse<T> result = (CommonResponse<T>) proceedingJoinPoint.proceed();
            MonitorEntry monitorEntry =
                    new MonitorEntry(
                            proceedingJoinPoint.getSignature().getName(),
                            seqNo,
                            result.getResponseCode(),
                            result.getResponseMessage(),
                            System.currentTimeMillis() - startTime);
            log.info("[{}]", JsonUtils.toJson(monitorEntry));
            return result;
        } catch (Exception e) {
            ResponseEnum ee = ResponseEnum.parse(e.getMessage());
            MonitorEntry monitorEntry =
                    new MonitorEntry(
                            proceedingJoinPoint.getSignature().getName(),
                            seqNo,
                            ee.getResponseCode(),
                            ee.getMessage(),
                            System.currentTimeMillis() - startTime);
            log.info("[{}]", JsonUtils.toJson(monitorEntry));
            throw e;
        }
    }
}
