package com.ziyi.controller;

import com.ziyi.api.domain.po.UserPO;
import com.ziyi.common.model.ResultData;
import com.ziyi.service.MongoDBService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author zhy
 * @data 2022/6/28 21:39
 */
@Slf4j
@RestController
@RequestMapping("/mongo")
public class TestMongoDBController {

    @Autowired
    private MongoDBService mongoDBService;

    @PostMapping("/insert")
    public String insert(@RequestBody UserPO userPO){
        return mongoDBService.insert(userPO);
    }

    @GetMapping("/query")
    public ResultData<UserPO> query(@RequestParam String id){
        return ResultData.succeed(mongoDBService.query(id));
    }

    @GetMapping("/queryByPage")
    public UserPO queryByPage(@RequestParam String id){
        return null;
    }

}
