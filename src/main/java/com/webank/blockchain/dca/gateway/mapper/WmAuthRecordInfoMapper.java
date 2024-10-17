package com.webank.blockchain.dca.gateway.mapper;

import com.webank.blockchain.dca.gateway.model.WmAddDelRecordInfo;
import com.webank.blockchain.dca.gateway.model.WmAuthRecordInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface WmAuthRecordInfoMapper {


    @Insert("insert into wm_auth_record_info " +
            "(" +
            "app_id," +
            "seq_no," +
            "algorithm," +
            "threshold," +
            "file_size," +
            "file_type," +
            "file_hash," +
            "file_meta," +
            "positive," +
            "similarity," +
            "return_unique_id," +
            "fail_reason," +
            "response_code," +
            "use_time," +
            "start_time," +
            "end_time" +
            ")" +
            " values " +
            "(" +
            "#{item.appId}," +
            "#{item.seqNo}," +
            "#{item.algorithm}," +
            "#{item.threshold}," +
            "#{item.fileSize}," +
            "#{item.fileType}," +
            "#{item.fileHash}," +
            "#{item.fileMeta}," +
            "#{item.positive}," +
            "#{item.similarity}," +
            "#{item.returnUniqueId}," +
            "#{item.failReason}," +
            "#{item.responseCode}," +
            "#{item.useTime}," +
            "#{item.startTime}," +
            "#{item.endTime}" +
            ")"
    )
    @Options(useGeneratedKeys = true, keyProperty = "pkId")
    int insert(@Param("item") WmAuthRecordInfo item);


}
