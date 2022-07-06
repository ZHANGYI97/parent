package com.ziyi.mongo.util;

import com.alibaba.fastjson.JSONObject;
import com.ziyi.api.domain.po.MongoPO;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author zhy
 * @data 2022/7/3 16:30
 */
@Component
public class MongoDBUtils {

    @Autowired
    MongoTemplate mongoTemplate;

    /**
     * 查询指定条件的全部集合
     *
     * @param jsonObject:查询条件
     * @param clazz:映射类
     * @param sort:排序字段
     * @return
     */
    public List<? extends MongoPO> find(JSONObject jsonObject, Class clazz, String sort) {

        Query query = new Query();
        for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
            if (entry.getValue() instanceof String) {
                String value = (String) entry.getValue();
                query.addCriteria(Criteria.where(entry.getKey()).is(value));
            } else if (entry.getValue() instanceof List) {
                List value = (List) entry.getValue();
                if (CollectionUtils.isNotEmpty(value)) {
                    if (value.get(0) instanceof String) {
                        query.addCriteria(Criteria.where(entry.getKey()).in(value));
                    }
                }
            }
        }
        query.with(Sort.by(Sort.Direction.DESC, sort));
        List<? extends MongoPO> resultList = mongoTemplate.find(query, clazz);
        return resultList;
    }

    /**
     * 查询指定条件的数据
     *
     * @param jsonObject:查询条件
     * @param clazz:映射类
     * @return
     */
    public <T extends MongoPO> T findOne(JSONObject jsonObject, Class clazz) {

        Query query = new Query();
        for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
            if (entry.getValue() instanceof String) {
                String value = (String) entry.getValue();
                query.addCriteria(Criteria.where(entry.getKey()).is(value));
            } else if (entry.getValue() instanceof List) {
                List value = (List) entry.getValue();
                if (CollectionUtils.isNotEmpty(value)) {
                    if (value.get(0) instanceof String) {
                        query.addCriteria(Criteria.where(entry.getKey()).in(value));
                    }
                }
            }
        }
        T reult = (T) mongoTemplate.findOne(query, clazz);
        return reult;
    }

    /**
     * 存储数据
     * @param obj
     */
    public void save(Object obj) {
        mongoTemplate.save(obj);
    }

    /**
     * 存储数据
     * @param obj
     */
    public void save(Object obj, String index) {
        mongoTemplate.save(obj, index);
    }

    /**
     * 更新数据
     * @param updateJson:需要更新的数据
     * @param queryJson:查询的数据
     */
    public void edit(JSONObject updateJson, JSONObject queryJson,Class clazz) {
        Query query = new Query();
        for (Map.Entry<String, Object> entry : queryJson.entrySet()) {
            if (entry.getValue() instanceof String) {
                String value = (String) entry.getValue();
                query.addCriteria(Criteria.where(entry.getKey()).is(value));
            } else if (entry.getValue() instanceof List) {
                List value = (List) entry.getValue();
                if (CollectionUtils.isNotEmpty(value)) {
                    if (value.get(0) instanceof String) {
                        query.addCriteria(Criteria.where(entry.getKey()).in(value));
                    }
                }
            }
        }
        Update update = new Update();
        for (Map.Entry<String, Object> entry : updateJson.entrySet()) {
            if (entry.getValue() instanceof String) {
                String value = (String) entry.getValue();
                query.addCriteria(Criteria.where(entry.getKey()).is(value));
            } else if (entry.getValue() instanceof List) {
                List value = (List) entry.getValue();
                if (CollectionUtils.isNotEmpty(value)) {
                    if (value.get(0) instanceof String) {
                        query.addCriteria(Criteria.where(entry.getKey()).in(value));
                    }
                }
            }
        }
        mongoTemplate.updateMulti(query, update, clazz);
    }

    /**
     * 删除记录
     * @param jsonObject
     * @param clazz
     */
    public void remove(JSONObject jsonObject, Class clazz) {
        Query query = new Query();
        for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
            if (entry.getValue() instanceof String) {
                String value = (String) entry.getValue();
                query.addCriteria(Criteria.where(entry.getKey()).is(value));
            } else if (entry.getValue() instanceof List) {
                List value = (List) entry.getValue();
                if (CollectionUtils.isNotEmpty(value)) {
                    if (value.get(0) instanceof String) {
                        query.addCriteria(Criteria.where(entry.getKey()).in(value));
                    }
                }
            }
        }
        mongoTemplate.remove(query, clazz);
    }

}
