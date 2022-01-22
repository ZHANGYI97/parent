package com.ziyi.mq.rocketmq;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.springframework.stereotype.Component;

/**
 * @author zhy
 * @data 2022/1/11 8:45 下午
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class RocketProducerClient {

    final DefaultMQProducer defaultMQProducer;

    /**
     * @param msg
     * @param topic
     * @param tag
     * @return
     */
    public SendResult sendMsg(String msg, String topic, String tag) {
        SendResult result = new SendResult();
        Message message = new Message(topic, tag, msg.getBytes());
        try {
            result = defaultMQProducer.send(message);
            log.info("消息ID" + result.getMsgId() + "消息发送状态" + result.getSendStatus());
        } catch (Exception e) {
            log.error("消息发送异常，异常信息为:", e);
        }
        return result;
    }

}
