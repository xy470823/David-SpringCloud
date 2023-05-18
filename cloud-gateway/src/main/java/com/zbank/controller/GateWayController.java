package com.zbank.controller;

import com.alibaba.fastjson2.JSONObject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GateWayController {

    @RequestMapping(value = "/gateway/test", method = RequestMethod.POST)
    public JSONObject echo(@RequestBody JSONObject reques) {
        reques.put("code","0000");
        reques.put("successMsg","响应成功");
        return reques;
    }
}
