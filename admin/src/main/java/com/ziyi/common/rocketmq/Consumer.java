package com.ziyi.common.rocketmq;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * auther:jurzis
 * date: 2021/2/25 8:58
 */
@Component
@RocketMQMessageListener(topic = "test-topic", consumerGroup = "my-consumer-group")
public class Consumer implements RocketMQListener<String> {

    @Override
    public void onMessage(String s) {
        System.out.println("我收到消息了！消息内容为："+s);
    }
}
