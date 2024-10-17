package com.webank.blockchain.dca.gateway.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WmAuthResponse {

    private boolean positive;
    private String uniqueId;
    private String falseReason;
}
