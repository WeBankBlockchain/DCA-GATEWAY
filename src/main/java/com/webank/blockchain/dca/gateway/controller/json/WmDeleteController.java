package com.webank.blockchain.dca.gateway.controller.json;


import com.webank.blockchain.dca.gateway.aspect.Record;
import com.webank.blockchain.dca.gateway.enums.ResponseEnum;
import com.webank.blockchain.dca.gateway.enums.TXType;
import com.webank.blockchain.dca.gateway.model.Context;
import com.webank.blockchain.dca.gateway.service.WmRegisterService;
import com.webank.blockchain.dca.gateway.service.WmStoreService;
import com.webank.blockchain.dca.gateway.vo.CommonResponse;
import com.webank.blockchain.dca.gateway.vo.DelWatermarkReq;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@Api(tags = "水印记录删除")
public class WmDeleteController {

    @Autowired
    WmStoreService storeService;
    @Autowired
    WmRegisterService registerService;

    @Record(type = "del")
    @PostMapping("/dca/delWatermark")
    public CommonResponse delWatermark(@RequestBody DelWatermarkReq delWatermarkReq) {

        Context context = Context.getContext();
        context.setAppId(delWatermarkReq.getAppId());
        context.setSeqNo(delWatermarkReq.getSeqNo());
        context.setUniqueId(delWatermarkReq.getUniqueId());
        context.setTxType(TXType.DEL.getCode());

        if (StringUtils.isEmpty(delWatermarkReq.getAppId())) {
            context.setResponseCode(ResponseEnum.No_App_Id.getResponseCode());
            return CommonResponse.error(ResponseEnum.No_App_Id);
        }
        if(!registerService.existAppId(delWatermarkReq.getAppId())){
            context.setResponseCode(ResponseEnum.AppId_Not_Exist.getResponseCode());
            return CommonResponse.error(ResponseEnum.AppId_Not_Exist);
        }
        if (StringUtils.isEmpty(delWatermarkReq.getUniqueId())) {
            context.setResponseCode(ResponseEnum.No_Unique_ID.getResponseCode());
            return CommonResponse.error(ResponseEnum.No_Unique_ID);
        }
        if (!storeService.existUniqueId(delWatermarkReq.getUniqueId(),delWatermarkReq.getAppId(), 0)){
            context.setResponseCode(ResponseEnum.UniqueID_Not_Exist.getResponseCode());
            return CommonResponse.error(ResponseEnum.UniqueID_Not_Exist);
        }
        return storeService.delWatermark(delWatermarkReq.getSeqNo(),delWatermarkReq.getUniqueId(),delWatermarkReq.getAppId());
    }
}
