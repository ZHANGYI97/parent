package com.ziyi.controller;

import com.ziyi.mq.rocketmq.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * auther:jurzis
 * date: 2021/4/15 18:51
 */
@RestController
@RequestMapping("/rocket")
public class TestRocketMQController {

    @Autowired
    private Producer producer;

    @GetMapping("/send")
    public void send(){
        producer.send();
    }

}
