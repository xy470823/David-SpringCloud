package com.zbank.feigna.bean;

import java.util.concurrent.atomic.AtomicLong;

public class SnowflakeId {
    // 64位ID结构
    private static final long WORKER_ID_BITS = 5L;   // 机器ID位数
    private static final long DATA_CENTER_ID_BITS = 5L; // 数据中心ID位数
    private static final long SEQUENCE_BITS = 12L;   // 序列号位数

    // 最大值
    private static final long MAX_WORKER_ID = ~(-1L << WORKER_ID_BITS);
    private static final long MAX_DATA_CENTER_ID = ~(-1L << DATA_CENTER_ID_BITS);
    private static final long SEQUENCE_MASK = ~(-1L << SEQUENCE_BITS);

    // 位移量
    private static final long WORKER_ID_SHIFT = SEQUENCE_BITS;
    private static final long DATA_CENTER_ID_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS;
    private static final long TIMESTAMP_LEFT_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS + DATA_CENTER_ID_BITS;

    // 基准时间戳（2010-11-04 01:42:54.657 UTC）
    private static final long EPOCH = 1288834974657L;

    // 状态变量
    private final long dataCenterId;
    private final long workerId;
    private final AtomicLong sequence = new AtomicLong(0);
    private final AtomicLong lastTimestamp = new AtomicLong(-1L);

    public SnowflakeId(long dataCenterId, long workerId) {
        // 参数校验
        if (dataCenterId > MAX_DATA_CENTER_ID || dataCenterId < 0) {
            throw new IllegalArgumentException(
                    String.format("DataCenterId must be between 0 and %d", MAX_DATA_CENTER_ID));
        }
        if (workerId > MAX_WORKER_ID || workerId < 0) {
            throw new IllegalArgumentException(
                    String.format("WorkerId must be between 0 and %d", MAX_WORKER_ID));
        }

        this.dataCenterId = dataCenterId;
        this.workerId = workerId;
    }

    public synchronized long nextId() {
        long timestamp = System.currentTimeMillis();

        // 检查时钟回退
        if (timestamp < lastTimestamp.get()) {
            throw new RuntimeException(
                    String.format("Clock moved backwards. Refusing to generate id for %d milliseconds",
                            lastTimestamp.get() - timestamp));
        }

        // 同一毫秒内生成
        if (lastTimestamp.get() == timestamp) {
            sequence.set((sequence.get() + 1) & SEQUENCE_MASK);
            // 序列号溢出，等待下一毫秒
            if (sequence.get() == 0) {
                timestamp = tilNextMillis(lastTimestamp.get());
            }
        } else {
            sequence.set(0);
        }

        lastTimestamp.set(timestamp);

        // 生成ID
        return ((timestamp - EPOCH) << TIMESTAMP_LEFT_SHIFT) |
                (dataCenterId << DATA_CENTER_ID_SHIFT) |
                (workerId << WORKER_ID_SHIFT) |
                sequence.get();
    }

    private long tilNextMillis(long lastTimestamp) {
        long timestamp = System.currentTimeMillis();
        while (timestamp <= lastTimestamp) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }
}
