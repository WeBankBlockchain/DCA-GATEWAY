package com.webank.blockchain.dca.gateway.aspect;

import com.webank.blockchain.dca.gateway.enums.ResponseEnum;
import com.webank.blockchain.dca.gateway.utils.JsonUtils;
import com.webank.blockchain.dca.gateway.vo.CommonRequest;
import com.webank.blockchain.dca.gateway.vo.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(20)
@Aspect
@Component
@Slf4j
public class SeqNoAspect {
    @Autowired private ThreadLocal<String> seqNoThreadLocal;

    @Pointcut("execution(public * com.webank.blockchain.dca.gateway.controller.json..*.*(..))")
    public void jsonReq() {}

    @Pointcut("execution(public * com.webank.blockchain.dca.gateway.controller.multipart..*.*(..))" +
            "&& !execution(public * com.webank.blockchain.dca.gateway.controller.multipart.WmUploadController.*(..)))")
    public void multipartReq() {}

    @Around("jsonReq()")
    public <T> Object doJsonBefore(ProceedingJoinPoint joinPoint) throws Throwable {
        CommonRequest request = (CommonRequest) joinPoint.getArgs()[0];
        if (StringUtils.isBlank(request.getSeqNo())) {
            return CommonResponse.error(ResponseEnum.Invalid_Request_Param, "seqNo is empty");
        }
        log.info("{} request is {},", joinPoint.getSignature().getName(), JsonUtils.toJson(request));
        seqNoThreadLocal.set(request.getSeqNo());
        CommonResponse<T> result = (CommonResponse<T>) joinPoint.proceed();
        result.setSeqNo(request.getSeqNo());
        seqNoThreadLocal.remove();
        return result;
    }

    @Around(value = "multipartReq()")
    public <T> Object doMultipartBefore(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        String seqNo = (String) proceedingJoinPoint.getArgs()[0];
        int fileType = (int) proceedingJoinPoint.getArgs()[1];
        if (StringUtils.isBlank(seqNo)) {
            return CommonResponse.error(ResponseEnum.Invalid_Request_Param, "seqNo is empty");
        }
        if (fileType != 0) {
            return CommonResponse.error(
                    ResponseEnum.Invalid_Request_Param, "not supported file type");
        }
        log.info("seqNo is {},", seqNo);
        seqNoThreadLocal.set(seqNo);
        CommonResponse<T> result = (CommonResponse<T>) proceedingJoinPoint.proceed();
        result.setSeqNo(seqNo);
        seqNoThreadLocal.remove();
        return result;
    }
}
