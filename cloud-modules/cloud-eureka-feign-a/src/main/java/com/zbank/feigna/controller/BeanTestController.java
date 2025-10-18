package com.zbank.feigna.controller;

import com.zbank.feigna.config.SnowflakeConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/beanTest")
public class BeanTestController {

    private static final Logger logger = LoggerFactory.getLogger(BeanTestController.class);

    @Resource
    private SnowflakeConfig snowflakeConfig;

    @GetMapping("/getId")
    public String getId() {
        logger.info("snowflakeConfig {}",snowflakeConfig);
        logger.info("snowflakeIdGenerator {}",snowflakeConfig.snowflakeIdGenerator());
        return String.valueOf(snowflakeConfig.snowflakeIdGenerator().nextId());
    }
}
