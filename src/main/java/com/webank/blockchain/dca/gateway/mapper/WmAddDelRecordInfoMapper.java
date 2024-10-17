package com.webank.blockchain.dca.gateway.mapper;

import com.webank.blockchain.dca.gateway.model.WmAddDelRecordInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface WmAddDelRecordInfoMapper {

    @Insert("insert into wm_add_delete_record_info " +
            "(" +
            "tx_type," +
            "seq_no," +
            "algorithm," +
            "app_id," +
            "unique_id," +
            "response_code," +
            "use_time," +
            "start_time," +
            "end_time" +
            ")" +
            " values " +
            "(" +
            "#{item.txType}," +
            "#{item.seqNo}," +
            "#{item.algorithm}," +
            "#{item.appId}," +
            "#{item.uniqueId}," +
            "#{item.responseCode}," +
            "#{item.useTime}," +
            "#{item.startTime}," +
            "#{item.endTime}" +
            ")"
    )
    @Options(useGeneratedKeys = true, keyProperty = "pkId")
    int insert(@Param("item") WmAddDelRecordInfo item);



}
