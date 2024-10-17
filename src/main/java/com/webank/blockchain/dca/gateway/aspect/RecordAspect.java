package com.webank.blockchain.dca.gateway.aspect;

import com.webank.blockchain.dca.gateway.enums.ResponseEnum;
import com.webank.blockchain.dca.gateway.enums.TXType;
import com.webank.blockchain.dca.gateway.mapper.WmAddDelRecordInfoMapper;
import com.webank.blockchain.dca.gateway.mapper.WmAuthRecordInfoMapper;
import com.webank.blockchain.dca.gateway.model.Context;
import com.webank.blockchain.dca.gateway.model.WmAddDelRecordInfo;
import com.webank.blockchain.dca.gateway.model.WmAuthRecordInfo;
import com.webank.blockchain.dca.gateway.utils.ThreadLocalUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Date;

@Aspect
@Component
@Slf4j
public class RecordAspect {

    @Autowired
    private WmAddDelRecordInfoMapper addDelRecordInfoMapper;

    @Autowired
    private WmAuthRecordInfoMapper authRecordInfoMapper;

    @Around("@annotation(com.webank.blockchain.dca.gateway.aspect.Record)")
    public <T> Object doJsonAround(ProceedingJoinPoint point) throws Throwable {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Object target = point.getTarget();
        Method method =
                target.getClass().getMethod(signature.getName(), signature.getParameterTypes());
        Record annotation = method.getAnnotation(Record.class);
        String recordType = annotation.type();
        long startTime = System.currentTimeMillis();
        Context context = Context.getContext();
        context.setTxType(TXType.parse(recordType).getCode());
        context.setStartTime(new Date(startTime));
        try {
            return point.proceed();
        } catch (Exception e) {
            ResponseEnum ee = ResponseEnum.parse(e.getMessage());
            Context.getContext().setResponseCode(ee.getResponseCode());
            throw e;
        }finally {
            long endTime = System.currentTimeMillis();
            long useTime = endTime - startTime;
            context.setEndTime(new Date(endTime));
            context.setUseTime(useTime);

            handleRecord();
            Context.clearContext();
        }
    }

    private void handleRecord() {
        Context context = Context.getContext();
        try {
            if (context.getTxType() == TXType.AUTH.getCode()) {
                WmAuthRecordInfo authRecordInfo = buildWmAuthRecordInfo(context);
                authRecordInfoMapper.insert(authRecordInfo);
            } else {
                WmAddDelRecordInfo addDelRecordInfo = buildWmAddDelRecordInfo(context);
                addDelRecordInfoMapper.insert(addDelRecordInfo);
            }
            log.info("handleRecord save record finish, appId = {} , seqNo = {}, txType = {}",
                    context.getAppId(),
                    context.getSeqNo(),
                    context.getTxType());
        }catch (Exception e){
            log.error("handleRecord OnError, appId = {} , seqNo = {},  txType = {}, reason is {}",
                    context.getAppId(),
                    context.getSeqNo(),
                    context.getTxType(),
                    e);
        }
    }

    private WmAddDelRecordInfo buildWmAddDelRecordInfo(Context context) {
        return WmAddDelRecordInfo.builder()
                .algorithm(context.getAlgorithm())
                .appId(context.getAppId())
                .seqNo(context.getSeqNo())
                .endTime(context.getEndTime())
                .startTime(context.getStartTime())
                .useTime(context.getUseTime())
                .responseCode(context.getResponseCode())
                .uniqueId(context.getUniqueId())
                .txType(context.getTxType())
                .build();
    }

    private WmAuthRecordInfo buildWmAuthRecordInfo(Context context) {
        return WmAuthRecordInfo.builder()
                .seqNo(context.getSeqNo())
                .algorithm(context.getAlgorithm())
                .appId(context.getAppId())
                .failReason(context.getFailReason())
                .endTime(context.getEndTime())
                .startTime(context.getStartTime())
                .similarity(context.getSimilarity())
                .fileHash(context.getFileHash())
                .fileMeta(context.getFileMeta())
                .fileSize(context.getFileSize())
                .fileType(context.getFileType())
                .positive(context.isPositive())
                .returnUniqueId(context.getReturnUniqueId())
                .threshold(context.getThreshold())
                .useTime(context.getUseTime())
                .responseCode(context.getResponseCode())
                .build();
    }

}
