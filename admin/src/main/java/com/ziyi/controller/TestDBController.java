package com.ziyi.controller;

import com.ziyi.entity.User;
import com.ziyi.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestDBController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/testDb")
    public User testDb(@RequestParam int id){
        User user = userMapper.select(id);
        return user;
    }

}
