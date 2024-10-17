package com.webank.blockchain.dca.gateway.mapper;

import com.webank.blockchain.dca.gateway.aspect.UseTime;
import com.webank.blockchain.dca.gateway.model.AppInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface WmAppInfoMapper {

    @UseTime
    @Insert(
            "INSERT INTO wm_app_info(app_id, app_name, description) VALUES(#{appId}, #{appName}, #{description})")
    int save(AppInfo appInfo);

    @Select("SELECT * FROM wm_app_info WHERE app_id = #{appId}")
    AppInfo findByAppId(String appId);
}
