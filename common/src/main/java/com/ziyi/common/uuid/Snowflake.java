package com.ziyi.common.uuid;

import com.ziyi.common.string.StringUtils;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;

/**
 * twitter snowflake.<br><br/>
 * 结构如下：（每部分用 - 分开）
 * 0 - 0000000000 0000000000 0000000000 0000000000 0 - 00000 - 00000 - 00000000000<br><br/>
 * 1标识位，由于long是2带符号的，最高位位符号位，正数是0，负数是1，id一般是正数，所以最高位是0<br><br/>
 * 41位时间戳（毫秒级），存储的是时间戳的差值（当前时间戳-起始时间戳）
 * 10位数据机器位，可以部署在1024个节点上，包含5位的datacenterId和5位wokerid
 * 12位序列，毫秒内的计数，12位的计数顺序号支持每个节点每毫秒（同一机器，同一时间戳）产生的4096个id序号
 * 加起来一共64位，即一个long型
 * */
public class Snowflake {

    /**
     * 起始时间戳
     * 一旦确定，不可变动
     */
    private final long twepoch = LocalDateTime
            .of(2021, Month.APRIL, 1, 0, 0, 0, 0)
            .atZone(ZoneId.of("GMT"))
            .toInstant().toEpochMilli();

    /**
     * id最大长度
     */
    public static final int MAX_ID_LENGTH = 19;

    public static final char ID_PADDING = '0';

    /**
     * 机器id所占位数
     */
    private static final long WORKER_ID_BITS = 5L;

    /**
     * 数据标识id所占位数
     */
    private static final long DATACENTER_ID_BITS = 5L;

    /**
     * 支持的最大机器标识id，结果是31
     */
    public static final long MAX_WORKER_ID = -1L^(-1L << WORKER_ID_BITS);

    /**
     * 支持的最大数据标识id，结果是31
     */
    public static final long MAX_DATACENTER_ID = -1L^(-1L << DATACENTER_ID_BITS);

    /**
     * 序列所占位数
     */
    private final long sequenceBits = 12L;

    /**
     * 机器id向左移12位
     */
    private final long workerIdShift = sequenceBits;

    /**
     * 数据标识id向左移17(12+5)位
     */
    private final long datacenterIdShift = sequenceBits + WORKER_ID_BITS;

    /**
     * 时间戳向左移22（5+5+12）位
     */
    private final long timestamoLeftShift = sequenceBits + WORKER_ID_BITS + DATACENTER_ID_BITS;

    /**
     * 生成序列掩码，这里是4095
     */
    private final long sequenceMask = -1L ^ (-1L << sequenceBits);

    /**
     * 工作机器id(0-31)
     */
    private long workerId;

    /**
     * 数据中心id（0-31）
     */
    private long dataceterId;

    /**
     * 毫秒内序列（0-4095）
     */
    private long sequence = 0L;

    /**
     * 上次生成id的时间戳
     */
    private long lastTimeStamp = -1L;

    /**
     * @param workerId 工作id(0-31)
     * @param dataceterId 数据中心id（0-31）
     */
    public Snowflake(long workerId, long dataceterId) {
        if (workerId > MAX_WORKER_ID || workerId < 0) {
            throw new IllegalArgumentException(
                    String.format("workerId cannot be greater than %d or less than 0", MAX_WORKER_ID)
            );
        }

        if (dataceterId > MAX_DATACENTER_ID || dataceterId < 0) {
            throw new IllegalArgumentException(
                    String.format("dataceterId cannot be greater than %d or less than 0", MAX_DATACENTER_ID)
            );
        }
        this.workerId = workerId;
        this.dataceterId = dataceterId;
    }

    public String nextStringId(){
        long next = nextId();
        return StringUtils.padLeft(String.valueOf(next), MAX_ID_LENGTH, ID_PADDING);
    }

    public synchronized long nextId(){
        long timestamp = timeGen();

        //如果当前时间小于上一次id生成的时间戳，说明系统异常，抛出异常
        if (timestamp < lastTimeStamp) {
            throw new RuntimeException(String.format("clock moved backwards.refusing to genera te id for %d milliseconds",
                    lastTimeStamp-timestamp));
        }

        if (lastTimeStamp == timestamp) {
            //同一时间生成的，进行毫秒内序列
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                //毫秒序列溢出
                timestamp = tilNextMillis(lastTimeStamp);
            }
        } else {
            //非同一时间生成，重置序列
            sequence = 0L;
        }

        lastTimeStamp = timestamp;
        return ((timestamp - twepoch) << timestamoLeftShift)
                | (dataceterId << datacenterIdShift)
                | (workerId << workerIdShift)
                | sequence;
    }

    /**
     * 阻塞到下一毫秒，直到获得新的时间戳
     * @param lastTimeStamp 上一次生成的时间戳id
     * @return
     */
    protected long tilNextMillis(long lastTimeStamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimeStamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    /**
     * 返回当前时间的毫秒
     * @return
     */
    protected long timeGen(){
        return System.currentTimeMillis();
    }
}
