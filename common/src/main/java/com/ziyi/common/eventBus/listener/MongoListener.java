package com.ziyi.common.eventBus.listener;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;
import com.ziyi.api.domain.entity.MongoEntity;

/**
 * @author zhy
 * @data 2022/7/7 20:37
 */
public class MongoListener {


    @Subscribe
    @AllowConcurrentEvents
    public void saveMongo(MongoEntity<?> mongoEntity, String collectionName) {
        System.out.println("saveMongo：{}" + mongoEntity);
    }

}
