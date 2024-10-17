package com.webank.blockchain.dca.gateway.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CommonRequest {
    @NotBlank private String seqNo;
}
