package com.webank.blockchain.dca.gateway.aspect;

import com.webank.blockchain.dca.gateway.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class RequestLogAspect {

    @Pointcut("execution(public * com.webank.blockchain.dca.gateway.controller..*.*(..)) " +
            "&& !execution(public * com.webank.blockchain.dca.gateway.controller.multipart.WmUploadController.*(..)))")
    public void requestPointCut() {}

    @AfterReturning(value = "requestPointCut()", returning = "o")
    public void afterRunningLog(Object o) {
        log.info("response is {}", JsonUtils.toJson(o));
    }
}
