package com.zbank.system.service.impl;

import com.zbank.system.service.vo.CharcObj;
import com.zbank.system.service.vo.StrategyResponse;
import com.zbank.system.service.vo.TaskContext;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class CharcTask {


    public StrategyResponse apply(TaskContext context){

        //筛选非承租人核查对象
        List<CharcObj> charcObjs = context.getCharcObjs().stream()
                .filter(e -> !e.getId().equals("1"))
                .collect(Collectors.toList());

        CharcObj c = context.getCharcObjs().stream().filter(e -> e.getId().equals("1")).findFirst().orElseThrow();

        List<CompletableFuture<StrategyResponse>> futures = new ArrayList<>();
        //执行非承租人决策流
        context.getNameList().forEach(name -> {
            //执行决策
            CompletableFuture<StrategyResponse> future = CompletableFuture.supplyAsync(StrategyResponse::new, context.getCharCExecutor()).exceptionally(ex -> {
                // 异常处理：返回默认值
                System.err.println("任务异常: " + ex.getMessage());
                return null; // 返回默认值
            });
            futures.add(future);
        });

        CompletableFuture<Void> allDoneFuture = CompletableFuture.allOf(
                futures.toArray(new CompletableFuture[0])
        );
        // 等待所有任务完成
        allDoneFuture.join();

        //执行承租人决策流
        futures.forEach(e-> System.out.printf(e.toString()));
        return new StrategyResponse();
    }
}
