package com.webank.blockchain.dca.gateway.model;

import com.webank.blockchain.dca.gateway.enums.ResponseEnum;
import com.webank.blockchain.dca.gateway.utils.ThreadLocalUtils;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class Context {

    public static Context getContext() {
        Context context = ThreadLocalUtils.get();
        if (context == null) {
            context = Context.builder()
                    .responseCode(ResponseEnum.Success.getResponseCode())
                    .fileHash("")
                    .fileMeta("")
                    .build();
            ThreadLocalUtils.set(context);
        }
        return context;
    }

    public static void clearContext() {
        ThreadLocalUtils.clear();
    }

    private String appId;

    private String seqNo;

    private String responseCode;

    private String uniqueId;

    private int txType;

    private int algorithm;

    private String returnUniqueId;

    private double threshold;

    private double fileSize;

    private int fileType;

    private String fileHash;

    private String fileMeta;

    private boolean positive;

    private float similarity;

    private int failReason;

    private long useTime;

    private Date startTime;

    private Date endTime;

}

