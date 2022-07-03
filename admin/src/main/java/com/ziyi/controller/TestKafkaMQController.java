package com.ziyi.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhy
 * @data 2022/6/21 21:17
 */
@Slf4j
@RestController
@RequestMapping("/kafka")
public class TestKafkaMQController {

    @Autowired
    private KafkaTemplate template;

    @RequestMapping("/sendMsg")
    public String sendMsg(String topic, String message) {
        log.info("tipic:{} ,+ message:{}", topic, message);
        template.send(topic, message);
        return "success";
    }

}
