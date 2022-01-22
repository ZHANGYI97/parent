package com.ziyi.mq.rocketmq.consumer;

import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zhy
 * 默认消费者
 * @data 2022/1/11 9:20 下午
 */
@Component
public class DefaultConsumerMQ extends AbstractRocketConsumer {

    /**
     * 初始化消费者
     */
    @Override
    public void init() {
        // 设置主题,标签与消费者标题
        super.necessary("orderTopic", "test", "这是标题");
        //消费者具体执行逻辑
        registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                msgs.forEach(msg -> {
                    System.out.printf("consumer message body %s %n", new String(msg.getBody()));
                });
                // 标记该消息已经被成功消费
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
    }
}
