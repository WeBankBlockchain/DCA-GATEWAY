package com.webank.blockchain.dca.gateway.mapper;

import com.webank.blockchain.dca.gateway.model.ThresholdInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ThresholdInfoMapper {

    @Select("SELECT * FROM threshold_info WHERE busi_name = #{busiName}")
    ThresholdInfo findByBusiName(String busiName);

    @Select("SELECT * FROM threshold_info")
    List<ThresholdInfo> findAll();

    @Insert("INSERT INTO threshod_info(busi_name, threshold) VALUES(#{busiName}, #{threshold})")
    int save(ThresholdInfo threshodInfo);
}
