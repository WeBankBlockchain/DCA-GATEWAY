package com.webank.blockchain.dca.gateway.service;

import cn.hutool.http.HttpUtil;
import com.webank.blockchain.dca.gateway.aspect.UseTime;
import com.webank.blockchain.dca.gateway.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@Slf4j
public class HttpService {

    @UseTime
    public String post(String url, HashMap<String, Object> paramMap) {
        log.info("http request url is: {}, request is: {}", url, JsonUtils.toJson(paramMap));
        String response = HttpUtil.post(url, paramMap);
        log.info("response is: {}", JsonUtils.toJson(response));
        return response;
    }
}
