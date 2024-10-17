package com.webank.blockchain.dca.gateway.utils;

import bsp.encrypt.EncryptUtil;
import bsp.encrypt.ParamType;

public class SecurityUtil {

    public static String decrypt(String encryptedPwd, String appPrivateKey, String sysPublicKey) {
        try {
            return EncryptUtil.decrypt(
                    ParamType.STRING,
                    sysPublicKey,
                    ParamType.STRING,
                    appPrivateKey,
                    ParamType.STRING,
                    encryptedPwd);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
