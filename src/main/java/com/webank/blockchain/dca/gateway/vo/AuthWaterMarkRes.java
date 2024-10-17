package com.webank.blockchain.dca.gateway.vo;

import lombok.Data;

@Data
public class AuthWaterMarkRes {
    private boolean positive;
    private String hashHex;
    private String failReason;
}
