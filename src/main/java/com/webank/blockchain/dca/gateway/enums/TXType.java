package com.webank.blockchain.dca.gateway.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TXType{

    UNKNOWN(0, "unknown"),
    ADD(1, "add"),
    AUTH(2, "auth"),
    DEL(3, "del");


    private int code;
    private String message;

    public static TXType parse(String message) {
        for (TXType e : TXType.values()) {
            if (e.getMessage().equals(message)) {
                return e;
            }
        }
        return UNKNOWN;
    }
}
