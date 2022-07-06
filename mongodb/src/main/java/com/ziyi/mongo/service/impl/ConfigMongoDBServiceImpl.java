package com.ziyi.mongo.service.impl;

import com.ziyi.api.domain.entity.MongoEntity;
import com.ziyi.common.date.DateUtils;
import com.ziyi.mongo.service.ConfigMongoDBService;
import com.ziyi.mongo.util.MongoDBUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author zhy
 * @data 2022/7/5 22:07
 */
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ConfigMongoDBServiceImpl implements ConfigMongoDBService {

    final MongoDBUtils mongoDBUtils;

    /**
     * 存储公共配置
     * @param mongoEntity:公共配置参数
     * @param collectionName:表名称
     */
    @Override
    @Async("asyncServiceExecutor")
    public void save(MongoEntity<?> mongoEntity, String collectionName) {
        mongoEntity.setCreatedDate(DateUtils.getTime());
        mongoDBUtils.save(mongoEntity, collectionName);
    }
}
