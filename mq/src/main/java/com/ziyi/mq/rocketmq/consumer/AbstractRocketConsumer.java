package com.ziyi.mq.rocketmq.consumer;

import org.apache.rocketmq.client.consumer.MQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.MessageListener;

/**
 * @author zhy
 * @data 2022/1/11 9:09 下午
 */
public abstract class AbstractRocketConsumer implements RocketConsumer {

    /**
     * 消费者消费的topics
     */
    public String topics;

    /**
     * 消费者消费的tags
     */
    public String tags;
    public MessageListener messageListener;

    /**
     * 消费者消费的title
     */
    public String consumerTitel;
    public MQPushConsumer mqPushConsumer;

    /**
     * 必要的信息
     *
     * @param topics
     * @param tags
     * @param consumerTitel
     */
    public void necessary(String topics, String tags, String consumerTitel) {
        this.topics = topics;
        this.tags = tags;
        this.consumerTitel = consumerTitel;
    }

    /**
     * 初始化
     */
    @Override
    public abstract void init();

    /**
     * 注册监听
     * @param messageListener
     */
    @Override
    public void registerMessageListener(MessageListener messageListener) {
        this.messageListener = messageListener;
    }

}
