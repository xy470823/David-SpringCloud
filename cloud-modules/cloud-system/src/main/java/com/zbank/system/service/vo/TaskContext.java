package com.zbank.system.service.vo;

import lombok.Data;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.List;

@Data
public class TaskContext {

    private ThreadPoolTaskExecutor charCExecutor;

    private List<String> nameList;

    private List<CharcObj> charcObjs;
}
