package com.zbank.provider.controller;

import com.alibaba.fastjson2.JSONObject;
import org.springframework.web.bind.annotation.*;

@RestController
public class FeignServiceController {

    @RequestMapping(value = "/feignProvider/test", method = RequestMethod.POST)
    public JSONObject echo(@RequestBody JSONObject reques) {
        reques.put("code","0000");
        reques.put("successMsg","响应成功");
        return reques;
    }
}
