package com.webank.blockchain.dca.gateway.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class ThresholdInfo {
    private long pkId;
    private String busiName;
    private String threshold;
    private Date createTime = new Date();
    private Date updateTime = new Date();
}
