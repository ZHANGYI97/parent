package com.ziyi.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.ziyi.common.base.annotation.SerializeAsSting;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.List;

import static com.alibaba.fastjson.JSON.DEFAULT_PARSER_FEATURE;

/**
 * 针对json 进行解析的工具类
 *
 * @author zhy
 */
public abstract class JsonUtils {
    static {
        ParserConfig.getGlobalInstance().setSafeMode(true);
    }

    public static <T> T parseObject(String text, TypeReference<T> type, Feature... features) {
        return JSONObject.parseObject(text, type, features);
    }

    public static String toJSONString(Object object) {
        return JSONObject.toJSONString(object, RESULT_JSON_FILTERS, SerializerFeature.WriteEnumUsingToString,
                SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullNumberAsZero,
                SerializerFeature.WriteNullListAsEmpty, SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.WriteNullBooleanAsFalse,
                SerializerFeature.WriteDateUseDateFormat,
                SerializerFeature.WriteBigDecimalAsPlain);
    }

    /**
     * 采用fastJson默认转化JSON数据
     *
     * @param object 需要转换的对象
     * @return JSON串
     */
    public static String toDefaultJSONString(Object object) {
        return JSONObject.toJSONString(object);
    }

    public static String toJSONString(Object object, SerializerFeature... features) {
        return JSONObject.toJSONString(object, features);
    }

    public static <T> T parseObject(String text, Class<T> clazz) {
        return JSONObject.parseObject(text, clazz);
    }

    public static <T> T parseObject(String json, Type type, Feature... features) {
        return JSON.parseObject(json, type, ParserConfig.global, DEFAULT_PARSER_FEATURE, features);
    }

    public static <T> List<T> parseArray(String text, Class<T> clazz) {
        return JSONObject.parseArray(text, clazz);
    }

    public static Object getValueBykey(String text, String key) {
        JSONObject jsonObject = JSON.parseObject(text);
        return jsonObject.get(key);
    }

    public static boolean isContainKey(String text, String key) {
        JSONObject jsonObject;
        try {
            jsonObject = JSON.parseObject(text);
        } catch (Exception e) {
            return false;
        }
        return jsonObject.containsKey(key);
    }

    public static JSONObject parseObject(String text) {
        return JSONObject.parseObject(text);
    }

    public static boolean isJson(String text) {
        try {
            parseObject(text);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static final ValueFilter RESULT_VALUE_FILTER = (object, name, value) -> {
        if (value == null) {
            return null;
        }
        try {
            Field field = object.getClass().getDeclaredField(name);
            if (null != field && null != field.getAnnotation(SerializeAsSting.class)) {
                return value.toString();
            }
            return value;
        } catch (Exception e) {
            return value;
        }
    };

    private final static SerializeFilter[] RESULT_JSON_FILTERS = new SerializeFilter[]
            {RESULT_VALUE_FILTER};
}
