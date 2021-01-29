package com.ziyi.controller;

import com.ziyi.common.email.SendEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * auther:jurzis
 * date: 2021/1/29 11:20
 */
@RestController
public class TestEmailController {

    private static final Logger logger = LoggerFactory.getLogger(TestEmailController.class);

    @Autowired
    private SendEmail sendEmail;

    @GetMapping("/testEmail")
    public String testSendEmail(){
        try {
            sendEmail.sendSimpleEmail("jurzis@163.com",
                    "761769823@qq.com",
                    "测试简单邮件发送",
                    "儿啊，爸爸邮件发成功了");
        } catch (Exception e) {
            logger.error(e.getMessage());
            return "send failed";
        }
        return "send success";

    }
}
