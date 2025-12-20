package com.zbank.system.service.vo;

import lombok.Data;

@Data
public class StrategyResponse {

    private CharcObj charcObj;

    private String message;

    public StrategyResponse(CharcObj charcObj, String message) {
        this.charcObj = charcObj;
        this.message = message;
    }
}
