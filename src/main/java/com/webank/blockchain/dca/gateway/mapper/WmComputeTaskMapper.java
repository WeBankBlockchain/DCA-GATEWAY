package com.webank.blockchain.dca.gateway.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface WmComputeTaskMapper {


    @Select("select count(1) from wm_compute_task where app_id = #{appId} and unique_id = #{uniqueId} and algorithm_type = #{algType} ")
    int existUniqueId(@Param("uniqueId")String uniqueId, @Param("appId")String appId, @Param("algType")int algType);

    @Select("select unique_id from wm_compute_task where app_id = #{appId} and watermark_file_hash = #{wmFileHash}")
    String queryIdByWatermarkFileHash(@Param("appId")String appId, @Param("wmFileHash") String wmFileHash);

    @Select("select watermark_file_hash from wm_compute_task where app_id = #{appId} and unique_id = #{uniqueId}")
    String queryHashByUniqueId(@Param("appId")String appId, @Param("uniqueId") String uniqueId);
}
