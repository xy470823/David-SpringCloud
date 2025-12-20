package com.zbank.system.service.impl;

import com.zbank.system.service.StrategyService;
import com.zbank.system.service.vo.CharcObj;
import com.zbank.system.service.vo.StrategyResponse;
import com.zbank.system.service.vo.TaskContext;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class StrategyServiceImpl implements StrategyService {

    @Resource
    @Qualifier("nameListExecutor")
    private ThreadPoolTaskExecutor nameListExecutor;

    @Resource
    @Qualifier("charCExecutor")
    private ThreadPoolTaskExecutor charCExecutor;

    @Override
    public Object apply() {
        //初始化上下文
        TaskContext context = new TaskContext();
        context.setCharCExecutor(charCExecutor);
        initNameList(context);
        initCharcObj(context);
        List<CompletableFuture<StrategyResponse>> futures = new ArrayList<>();
        //执行子线程
        context.getNameList().forEach(name -> {
            CompletableFuture<StrategyResponse> future = CompletableFuture.supplyAsync(() -> {
                CharcTask task = new CharcTask();
                return task.apply(name,context);
            }, nameListExecutor).exceptionally(ex -> {
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
        futures.forEach(e-> System.out.println(e.toString()));
        return null;
    }

    private void initNameList(TaskContext context){
        List<String> nameList = new ArrayList<>();
        nameList.add("中登名单一");
        nameList.add("中登名单二");
        nameList.add("中登名单三");
        nameList.add("中登名单四");
        nameList.add("中登名单五");
        context.setNameList(nameList);
    }

    private void initCharcObj(TaskContext context){
        List<CharcObj> charcObjs = new ArrayList<>();
        charcObjs.add(new CharcObj("1"));
        charcObjs.add(new CharcObj("2"));
        charcObjs.add(new CharcObj("3"));
        charcObjs.add(new CharcObj("3"));
        charcObjs.add(new CharcObj("3"));
        context.setCharcObjs(charcObjs);
    }
}
