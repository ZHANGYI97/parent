package com.ziyi.service.impl;

import com.ziyi.service.TestService;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService {


    @Override
    public String test() {
        return "test Success";
    }
}
