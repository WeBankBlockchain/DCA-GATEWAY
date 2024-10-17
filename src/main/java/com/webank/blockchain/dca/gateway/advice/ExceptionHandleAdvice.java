package com.webank.blockchain.dca.gateway.advice;

import com.webank.blockchain.dca.gateway.enums.ResponseEnum;
import com.webank.blockchain.dca.gateway.vo.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.net.ConnectException;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class ExceptionHandleAdvice {
    @Autowired private ThreadLocal<String> seqNoThreadLocal;

    @ExceptionHandler(value = ConnectException.class)
    @ResponseBody
    public <T> CommonResponse<T> connectException(HttpServletRequest req, ConnectException e) {
        log.error("OnError: ConnectException encountered ", e);
        CommonResponse<T> result =
                CommonResponse.error(ResponseEnum.Request_Timeout, e.getMessage());
        result.setSeqNo(seqNoThreadLocal.get());
        seqNoThreadLocal.remove();
        return result;
    }

    @ExceptionHandler(value = DuplicateKeyException.class)
    @ResponseBody
    public <T> CommonResponse<T> duplicateKeyException(
            HttpServletRequest req, DuplicateKeyException e) {
        log.error("OnError: DuplicateKeyException encountered ", e);
        CommonResponse<T> result =
                CommonResponse.error(ResponseEnum.Duplicate_Data_Item, e.getMessage());
        result.setSeqNo(seqNoThreadLocal.get());
        seqNoThreadLocal.remove();
        return result;
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public <T> CommonResponse<T> methodArgumentNotValidExceptionHandler(
            HttpServletRequest req, MethodArgumentNotValidException e) {
        log.error("OnError: Exception encountered ", e);
        String message =
                e.getBindingResult().getAllErrors().stream()
                        .map(ObjectError::getDefaultMessage)
                        .collect(Collectors.joining("; "));
        CommonResponse<T> result = CommonResponse.error(ResponseEnum.Invalid_Request_Param);
        result.setDebugMessage(message);
        result.setSeqNo(seqNoThreadLocal.get());
        seqNoThreadLocal.remove();
        return result;
    }

    @ExceptionHandler(value = BindException.class)
    @ResponseBody
    public <T> CommonResponse<T> bindExceptionHandler(HttpServletRequest req, BindException e) {
        log.error("OnError: Exception encountered ", e);
        String message =
                e.getAllErrors().stream()
                        .map(ObjectError::getDefaultMessage)
                        .collect(Collectors.joining("; "));
        CommonResponse<T> result = CommonResponse.error(ResponseEnum.Invalid_Request_Param);
        result.setDebugMessage(message);
        result.setSeqNo(seqNoThreadLocal.get());
        seqNoThreadLocal.remove();
        return result;
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseBody
    public <T> CommonResponse<T> constraintViolationExceptionHandler(
            HttpServletRequest req, ConstraintViolationException e) {
        log.error("OnError: Exception encountered ", e);
        String message =
                e.getConstraintViolations().stream()
                        .map(ConstraintViolation::getMessage)
                        .collect(Collectors.joining("; "));
        CommonResponse<T> result = CommonResponse.error(ResponseEnum.Invalid_Request_Param);
        result.setDebugMessage(message);
        result.setSeqNo(seqNoThreadLocal.get());
        seqNoThreadLocal.remove();
        return result;
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public <T> CommonResponse<T> exceptionHandler(HttpServletRequest req, Exception e) {
        log.error("OnError: Exception encountered ", e);
        ResponseEnum ee = ResponseEnum.parse(e.getMessage());
        CommonResponse<T> result = CommonResponse.error(ee, e.getMessage());
        result.setSeqNo(seqNoThreadLocal.get());
        seqNoThreadLocal.remove();
        return result;
    }
}
