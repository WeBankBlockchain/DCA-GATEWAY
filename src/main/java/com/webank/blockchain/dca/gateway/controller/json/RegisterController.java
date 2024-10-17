package com.webank.blockchain.dca.gateway.controller.json;

import com.webank.blockchain.dca.gateway.service.RegisterService;
import com.webank.blockchain.dca.gateway.utils.JsonUtils;
import com.webank.blockchain.dca.gateway.vo.AddCollectionReq;
import com.webank.blockchain.dca.gateway.vo.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class RegisterController {
    @Autowired RegisterService registerService;

    @PostMapping("/dca/addCollection")
    public <T> CommonResponse<T> addCollection(@RequestBody AddCollectionReq addCollectionReq) {
        log.info("addCollection request is {}", JsonUtils.toJson(addCollectionReq));
        return registerService.registerCollection(addCollectionReq);
    }
}
