package com.ziyi.mongo.service;

import com.ziyi.api.domain.entity.MongoEntity;

/**
 * 公共配置mongodb存储
 * @author zhy
 * @data 2022/7/5 22:01
 */
public interface ConfigMongoDBService {

    /**
     * 存储公共配置
     * @param mongoEntity:公共配置参数
     * @param collectionName:表名称
     */
    void save(MongoEntity<?> mongoEntity, String collectionName);

}
