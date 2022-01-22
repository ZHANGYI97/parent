package com.ziyi.mq.rocketmq.consumer;

import org.apache.rocketmq.client.consumer.listener.MessageListener;

/**
 * @author zhy
 * @data 2022/1/11 9:08 下午
 */
public interface RocketConsumer {

    /**
     * 初始化消费者
     */
    public abstract void init();

    /**
     * 注册监听
     *
     * @param messageListener
     */
    public void registerMessageListener(MessageListener messageListener);

}
