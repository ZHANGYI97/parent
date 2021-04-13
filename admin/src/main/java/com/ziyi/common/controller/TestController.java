package com.ziyi.common.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * auther:jurzis
 * date: 2021/2/25 9:10
 */
@RestController("/test1")
public class TestController {

    @GetMapping("/test")
    public String test(){
        return "success";
    }

}
