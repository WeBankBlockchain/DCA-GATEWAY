package com.webank.blockchain.dca.gateway.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FileType{

    UNKNOWN(0, "unknown"),
    PNG(1, "png"),
    JPG(2, "jpg");


    private int code;
    private String message;

    public static FileType parse(String message) {
        for (FileType e : FileType.values()) {
            if (e.getMessage().equals(message)) {
                return e;
            }
        }
        return UNKNOWN;
    }
}