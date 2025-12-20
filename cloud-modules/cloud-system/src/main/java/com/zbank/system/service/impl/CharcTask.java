package com.zbank.system.service.impl;

import com.alibaba.fastjson2.JSONArray;
import com.zbank.system.service.vo.CharcObj;
import com.zbank.system.service.vo.StrategyResponse;
import com.zbank.system.service.vo.TaskContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class CharcTask {


    private static final Logger log = LoggerFactory.getLogger(CharcTask.class);

    public StrategyResponse apply(String name,TaskContext context){
        try {
            List<CharcObj> charcObjs = JSONArray.parseArray(JSONArray.toJSONString(context.getCharcObjs()), CharcObj.class);
            CharcObj charcObj = charcObjs.stream().filter(e -> e.getId().equals("1")).findFirst().orElseThrow(() -> new RuntimeException("实控人对象不存在"));
            //筛选出非承租人核查对象(移除承租人核查对象)
            charcObjs.remove(charcObj);

            List<CompletableFuture<StrategyResponse>> futures = new ArrayList<>();
            //执行非承租人决策流
            charcObjs.forEach(charc -> {
                //执行决策
                CompletableFuture<StrategyResponse> future = CompletableFuture.supplyAsync(() -> new StrategyResponse(charc, "success"),context.getCharCExecutor())
                        .exceptionally(ex -> {
                            return new StrategyResponse(charc, ex.getMessage()); // 返回默认值
                        });
                futures.add(future);
            });

            CompletableFuture<Void> allDoneFuture = CompletableFuture.allOf(
                    futures.toArray(new CompletableFuture[0])
            );
            // 等待所有任务完成
            allDoneFuture.join();
            log.info("{}-------->非承租人执行完成",name);
            //执行承租人决策流
            futures.forEach(e-> System.out.println(e.toString()));
            log.info("{}-------->承租人执行完成",name);

            return new StrategyResponse(charcObj, "success");
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            //组装异常结果
            return new StrategyResponse(null, "success");
        }
    }
}
