package com.webank.blockchain.dca.gateway.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WmAuthRecordInfo {

    private long pkId;

    private String seqNo;

    private String appId;

    private String responseCode;;

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
