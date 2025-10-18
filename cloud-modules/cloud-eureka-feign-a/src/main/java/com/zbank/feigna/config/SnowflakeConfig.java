package com.zbank.feigna.config;

import com.zbank.feigna.bean.SnowflakeId;
import com.zbank.feigna.cache.LocalCache;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class SnowflakeConfig {

    @Resource
    private LocalCache localCache;

    @Bean
    public SnowflakeId snowflakeIdGenerator() {
        // 从配置文件或环境变量获取dataCenterId和machineId
        // 这里直接使用固定值，实际应用中应从配置获取
        return localCache.getSnowflakeId();
    }
}
