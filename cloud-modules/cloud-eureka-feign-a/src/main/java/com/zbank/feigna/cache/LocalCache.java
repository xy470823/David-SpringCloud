package com.zbank.feigna.cache;


import com.alibaba.fastjson2.JSON;
import com.alibaba.ttl.TransmittableThreadLocal;
import com.zbank.feigna.EurekaFeignaApplication;
import com.zbank.feigna.bean.SnowflakeId;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class LocalCache implements ApplicationRunner, Serializable {

    private static final long serialVersionUID = 4445556632565335L;

    private static final Logger logger = LoggerFactory.getLogger(LocalCache.class);

    private static final TransmittableThreadLocal<Map<String, Object>> ThreadLocal = new TransmittableThreadLocal<>();

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("项目启动时加载本地缓存数据");
        set("ServerName", EurekaFeignaApplication.class.getName());
        set("SnowflakeId", new SnowflakeId(1, 1));
        logger.info("项目启动时加载本地缓存完成:{}", JSON.toJSONString(ThreadLocal.get()));
    }

    public void set(String key, Object value) {
        Map<String, Object> localMap = getThreadLocalMap();
        localMap.put(key, value == null ? StringUtils.EMPTY : value);
    }

    public Object get(String key){
        Map<String, Object> localMap = getThreadLocalMap();
        return localMap.getOrDefault(key,StringUtils.EMPTY);
    }

    public Map<String, Object> getThreadLocalMap() {
        Map<String, Object> localMap = ThreadLocal.get();
        if (null == localMap) {
            localMap = new ConcurrentHashMap<>();
            ThreadLocal.set(localMap);
        }
        return localMap;
    }

    public SnowflakeId getSnowflakeId() {
        Map<String, Object> localMap = ThreadLocal.get();
        if (null == localMap) {
            localMap = new ConcurrentHashMap<>();
            ThreadLocal.set(localMap);
        }
        return (SnowflakeId) localMap.get("SnowflakeId");
    }
}
