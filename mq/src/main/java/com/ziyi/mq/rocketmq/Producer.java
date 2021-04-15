package com.ziyi.mq.rocketmq;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * auther:jurzis
 * date: 2021/2/25 8:55
 */
@Component
public class Producer {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    public void send(){
        rocketMQTemplate.convertAndSend("test-topic", "你好,Java");
    }

}
