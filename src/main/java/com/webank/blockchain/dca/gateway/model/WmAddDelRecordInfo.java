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
public class WmAddDelRecordInfo {

    private long pkId;

    private String seqNo;

    private int algorithm;

    private String uniqueId;

    private int txType;

    private String appId;

    private String responseCode;

    private long useTime;

    private Date startTime;

    private Date endTime;


}
