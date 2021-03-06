package com.ziyi.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ziyi.api.domain.po.UserPO;
import com.ziyi.common.base.exception.ZiRuntimeException;
import com.ziyi.common.base.exception.ZiServerException;
import com.ziyi.common.uuid.IdGenUtils;
import com.ziyi.mongo.util.MongoDBUtils;
import com.ziyi.service.MongoDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

/**
 * @author zhy
 * @data 2022/6/28 21:52
 */
@Service
public class MongoDBServiceImpl implements MongoDBService {

    @Autowired
    MongoDBUtils mongoDBUtils;

    @Override
    public String insert(UserPO userPO) {
        String id = IdGenUtils.snowflakeGen().nextIdStr();
        userPO.setId(id);
        mongoDBUtils.save(userPO);
        return id;
    }

    @Override
    public UserPO query(String id) {
        UserPO userPO = new UserPO();
        userPO.setId(id);
        return mongoDBUtils.findOne((JSONObject) JSONObject.toJSON(userPO), UserPO.class);
    }

}
