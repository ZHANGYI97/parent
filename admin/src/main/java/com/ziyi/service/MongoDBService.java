package com.ziyi.service;

import com.ziyi.api.domain.po.UserPO;

/**
 * @author zhy
 * @data 2022/6/28 21:51
 */
public interface MongoDBService {

    String insert(UserPO userPO);

    UserPO query(String id);

}
