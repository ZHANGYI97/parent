package com.ziyi.mq.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author zhy
 * @data 2022/6/21 21:16
 */
@Component
public class KafkaConsumer {

    @KafkaListener(topics = {"partopic"})
    public void listen(ConsumerRecord record){
        System.out.println("+++++" + record.topic()+":"+record.value());
    }

}
