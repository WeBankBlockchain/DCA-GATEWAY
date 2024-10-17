package com.webank.blockchain.dca.gateway.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class AppInfo {
    private long pkId;
    private String appId;
    private String appName;
    private String description;
    private Date createTime = new Date();
    private Date updateTime = new Date();
}
