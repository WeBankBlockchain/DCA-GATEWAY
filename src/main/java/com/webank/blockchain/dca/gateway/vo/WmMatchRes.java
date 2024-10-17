package com.webank.blockchain.dca.gateway.vo;

import lombok.Data;

@Data
public class WmMatchRes {
    private boolean positive;
    private String similarity;
    private String uniqueId;
    private String failReason;
}
