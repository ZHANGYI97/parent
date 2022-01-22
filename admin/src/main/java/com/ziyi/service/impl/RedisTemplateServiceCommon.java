package com.ziyi.service.impl;

import com.ziyi.entity.TestTranslate;
import com.ziyi.mapper.TestTranslateMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RedisTemplateServiceCommon {

    @Autowired
    TestTranslateMapper testTranslateMapper;

    @Transactional(rollbackFor = Exception.class)
    public void testTranslate(){
        TestTranslate testTranslate = TestTranslate.builder().build();
        testTranslate.setId(1);
        testTranslate.setName("zhy");
        testTranslate.setAge(1);
        testTranslate.setAddress("wuhan");
        testTranslateMapper.insert(testTranslate);
        TestTranslate testTranslate1 = TestTranslate.builder().build();
        testTranslate1.setId(1);
        testTranslate1.setName("zhy");
        testTranslate1.setAge(122);
        testTranslate1.setAddress("wuhan");
        testTranslateMapper.inserts(testTranslate1);
    }


}
