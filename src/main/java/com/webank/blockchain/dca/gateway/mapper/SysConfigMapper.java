package com.webank.blockchain.dca.gateway.mapper;

import com.webank.blockchain.dca.gateway.aspect.UseTime;
import com.webank.blockchain.dca.gateway.model.SysConfig;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SysConfigMapper {

    @Select("SELECT * FROM sys_config WHERE config_name = #{configName}")
    SysConfig findByConfigName(String configName);

    @Select("SELECT * FROM sys_config")
    List<SysConfig> findAll();

    @UseTime
    @Insert(
            "INSERT INTO sys_config( config_name, config_value) VALUES( #{configName}, #{configValue})")
    int save(SysConfig sysConfig);
}
