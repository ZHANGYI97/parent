package com.ziyi.common.uuid;

import java.lang.management.ManagementFactory;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Random;

/**
 * 基于snowflak算法的唯一键值生成器，线程安全
 */
public class IDGeneratorUtils {

    private static final Random SHARED_RANDOM = new Random();

    private static final int POSITIVE_MASK = 0x7FFFFFFF;

    private static class Holder {
        public static Snowflake instance = new Snowflake(generateWorkerId(), generateDatacenterId());
    }

    /**
     * 生成一个long类型的唯一id
     * @return
     */
    public static long nextId(){
        return Holder.instance.nextId();
    }

    /**
     * 生成一个string的唯一id
     * @return
     */
    public static String nextStringId(){
        return Holder.instance.nextStringId();
    }

    /**
     * 自定义前缀 + 生成一个string的唯一id
     * @param prefix
     * @return
     */
    public static String nextStringId(String prefix) {
        return prefix + Holder.instance.nextStringId();
    }

    /**
     * 根据设备网卡信息生成workerId
     * @return
     */
    public static long generateWorkerId(){
        int workerId;
        try {
            StringBuilder builder = new StringBuilder();
            Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces();
            while (e.hasMoreElements()) {
                builder.append(e.nextElement().toString());
            }
            workerId = builder.toString().hashCode();
        } catch (SocketException ex) {
            workerId = nextRandomInt();
        }
        return workerId % Snowflake.MAX_WORKER_ID;
    }

    /**
     * 通过进程号id和类加载器id生成datacenterid
     * @return
     */
    public static long generateDatacenterId(){
        int datacenterId;
        int processId;
        try {
            processId = ManagementFactory.getRuntimeMXBean().getName().hashCode();
        } catch (Throwable e) {
            processId = nextRandomInt();
        }

        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        int loaderId = loader != null ? System.identityHashCode(loader) : 0;
        StringBuilder builder = new StringBuilder();
        builder.append(Integer.toHexString(processId));
        builder.append(Integer.toHexString(loaderId));
        datacenterId = builder.toString().hashCode() & 0xFFF;

        return datacenterId & Snowflake.MAX_DATACENTER_ID;
    }

    private static int nextRandomInt() {
        return SHARED_RANDOM.nextInt() & POSITIVE_MASK;
    }

}
