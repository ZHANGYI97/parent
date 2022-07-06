package com.ziyi.api.domain.entity;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * 【类或接口功能描述】 MongoDB通用保存Entity
 *
 * @author zhy
 * @version 1.0.0
 */
@Data
@AllArgsConstructor
public class MongoEntity<T extends Object> {

    /**
     * 业务唯一号
     */
    @Field("traceId")
    private String traceId;

    /**
     * 请求方法
     */
    @Field("requetMethod")
    private String requetMethod;

    /**
     * 业务类型
     */
    @Field("bizType")
    private String bizType;
    /**
     * 待存储数据
     */
    @Field("datas")
    private T datas;

    /**
     * 耗时
     */
    @Field("timeConsuming")
    private long timeConsuming;

    /**
     * 创建时间
     */
    @Field("createdDate")
    private String createdDate;
}
