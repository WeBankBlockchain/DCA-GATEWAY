package com.webank.blockchain.dca.gateway.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FailReason{

    SUCCESS(0, "success"),
    VEC_NOT_EXIST(1, "Watermark decrypt is not valid"),
    AI_NOT_PASS(2, "the image similarity is less than the threshold"),
    EXTRACT_ERROR(3, "extract watermark failed,please confirm whether the watermark picture is correct");


    private int code;
    private String message;

    public static FailReason parse(String message) {
        for (FailReason e : FailReason.values()) {
            if (e.getMessage().equals(message)) {
                return e;
            }
        }
        return SUCCESS;
    }
}
