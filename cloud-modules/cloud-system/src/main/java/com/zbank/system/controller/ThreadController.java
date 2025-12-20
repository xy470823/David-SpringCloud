package com.zbank.system.controller;


import com.zbank.feigna.vo.ResponseTestVO;
import com.zbank.system.service.StrategyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/thread")
public class ThreadController {

    @Resource
    private StrategyService strategyService;

    @GetMapping("/apply")
    public ResponseTestVO apply() {
        strategyService.apply();
        return new ResponseTestVO();
    }
}
