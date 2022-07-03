package com.ziyi.config.web.client;

import com.ziyi.common.base.exception.ZiRuntimeException;
import com.ziyi.common.constants.MsgCodeConstants;
import com.ziyi.common.model.ResultData;
import com.ziyi.common.utils.JsonUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

/**
 * @author zhy
 * @date 2022-7-3
 */
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@ConditionalOnMissingBean(value = {SpringCloudClient.class})
@Order
public class SpringCloudClient {

    RestTemplate restTemplate;
    private final HttpHeaders rpcPostHttpHeaders = new RpcPostHttpHeaders();

    /**
     * Get方式请求服务
     *
     * @param serviceUrl 请求服务的URl
     * @return 请求结果 JSON字符串
     */
    public String get(String serviceUrl) {
        String result;
        try {
            log.debug("//// GET方式请求调用：{}", serviceUrl);
            result = restTemplate.getForObject(serviceUrl.trim(), String.class);
            log.debug("//// POST方式调用结果：{}", result);
        } catch (RestClientException e) {
            log.error("//// GET方式调用：{},异常{}" + serviceUrl, e);
            throw new ZiRuntimeException(e);
        }
        return result;
    }

    /**
     * POST方式请求服务
     *
     * @param serviceUrl 请求服务的URl
     * @param jsonStr    请求服务参数
     * @return 请求结果 JSON字符串
     */
    public String post(String serviceUrl, String jsonStr) {
        String result;
        try {
            log.debug("//// POST方式请求调用：{},参数：{}", serviceUrl, jsonStr);
            result = restTemplate.postForObject(serviceUrl.trim(), new HttpEntity<>(jsonStr, rpcPostHttpHeaders), String.class);
            log.debug("//// POST方式调用结果：{}", result);
        } catch (RestClientException e) {
            log.error("//// POST方式调用异常：url:{},参数：{},case:{}", serviceUrl, jsonStr, e);
            throw new ZiRuntimeException(e);
        }
        return result;
    }

    /**
     * POST方式请求服务
     *
     * @param serviceUrl 请求服务的URl
     * @param jsonStr    请求服务参数
     * @return 请求结果ResultData
     */
    public ResultData restfulPost(String serviceUrl, String jsonStr) {
        String result = post(serviceUrl, jsonStr);
        if (ObjectUtils.isEmpty(result)) {
            log.error("///POST方式调用返回参数为空：URL:{},参数：{}，返回数据：{}", serviceUrl, jsonStr, result);
            throw new ZiRuntimeException(MsgCodeConstants.SYSTEM_ERROR);
        }
        return JsonUtils.parseObject(result, ResultData.class);
    }

    /**
     * Get方式请求服务
     *
     * @param serviceUrl 请求服务的URl
     * @return 请求结果 ResultData
     */
    public ResultData restfulGet(String serviceUrl) throws ZiRuntimeException {
        String result = get(serviceUrl);
        if (ObjectUtils.isEmpty(result)) {
            log.error("///GET方式调用返回参数为空：URL:{},返回数据：{}", serviceUrl, result);
            // TODO
            throw new ZiRuntimeException(MsgCodeConstants.PARAM_CAN_NOT_BE_EMPTY);
        }

        return JsonUtils.parseObject(result, ResultData.class);
    }

    /**
     * GET方式请求服务
     *
     * @param serviceUrl 请求服务的URl
     * @param cls        需要转换的对象
     * @return 返回需要的集合对象
     */
    public <T> List<T> restfulGetList(String serviceUrl, Class<T> cls) throws ZiRuntimeException {
        ResultData resultData = restfulGet(serviceUrl);

        if (!resultData.isSucceed()) {
            log.error("////调用服务返回false,case:{},URL:{}" + resultData.getErrorMsg(), serviceUrl);
            throw new ZiRuntimeException(resultData.getErrorCode(), resultData.getErrorMsg(), "");
        }
        if (Objects.isNull(resultData.getData())) {
            log.warn("////调用服务返回null,result:{}", resultData.getData());
            return null;
        }
        return JsonUtils.parseArray(resultData.getData().toString(), cls);
    }

    /**
     * GET方式请求服务
     *
     * @param serviceUrl 请求服务的URl
     * @param cls        需要转换的对象
     * @return 请求结果ResultData
     */
    public <T> T restfulGetClass(String serviceUrl, Class<T> cls) {
        ResultData resultData = restfulGet(serviceUrl);
        if (!resultData.isSucceed()) {
            log.error("////调用服务返回false,case:{},URL:" + resultData.getErrorMsg(), serviceUrl);
            throw new ZiRuntimeException(resultData.getErrorCode(), resultData.getErrorMsg(), "");
        }
        if (Objects.isNull(resultData.getData())) {
            log.warn("////调用服务返回null,result:{}", resultData.getData());
            return null;
        }
        return JsonUtils.parseObject(resultData.getData().toString(), cls);
    }

    /**
     * GET方式请求服务
     *
     * @param serviceUrl 请求服务的URl
     * @return 请求结果resultData.getData()
     */
    public String restfulGetStr(String serviceUrl) throws ZiRuntimeException {
        ResultData resultData = restfulGet(serviceUrl);
        if (!resultData.isSucceed()) {
            log.error("////调用服务返回false,case:{},URL:{}", resultData.getErrorMsg(), serviceUrl);
            throw new ZiRuntimeException(resultData.getErrorCode(), resultData.getErrorMsg(), "");
        }
        if (Objects.isNull(resultData.getData())) {
            log.warn("////调用服务返回null,result:{}", resultData.getData());
            return null;
        }
        return String.valueOf(resultData.getData());
    }

    /**
     * POST方式请求服务
     *
     * @param serviceUrl 请求服务的URl
     * @param jsonStr    请求服务参数
     * @param cls        需要转换的对象
     * @return 返回需要的集合对象
     */
    public <T> List<T> restfulPostList(String serviceUrl, String jsonStr, Class<T> cls) throws ZiRuntimeException {
        ResultData resultData = restfulPost(serviceUrl, jsonStr);

        if (!resultData.isSucceed()) {
            log.error("调用服务返回false,case:" + resultData.getErrorMsg() + "URL:" + serviceUrl + " param:" + jsonStr);
            throw new ZiRuntimeException(resultData.getErrorCode(), resultData.getErrorMsg(), "");
        }
        if (Objects.isNull(resultData.getData())) {
            log.warn("调用服务返回null,result:" + resultData.getData());
            return null;
        }
        return JsonUtils.parseArray(resultData.getData().toString(), cls);
    }

    /**
     * POST方式请求服务
     *
     * @param serviceUrl 请求服务的URl
     * @param jsonStr    请求服务参数
     * @param cls        需要转换的对象
     * @return 请求结果ResultData
     */
    public <T> T restfulPostClass(String serviceUrl, String jsonStr, Class<T> cls) throws ZiRuntimeException {
        ResultData resultData = restfulPost(serviceUrl, jsonStr);
        if (!resultData.isSucceed()) {
            log.error("调用服务返回false,case:" + resultData.getErrorMsg() + "URL:" + serviceUrl + " param:" + jsonStr);
            throw new ZiRuntimeException(resultData.getErrorCode(), resultData.getErrorMsg(), "");
        }
        if (Objects.isNull(resultData.getData())) {
            log.warn("调用服务返回null,result:" + resultData.getData());
            return null;
        }
        return JsonUtils.parseObject(resultData.getData().toString(), cls);
    }

    /**
     * POST方式请求服务
     *
     * @param serviceUrl 请求服务的URl
     * @param jsonStr    请求服务参数
     * @return 请求结果ResultData
     */
    public String restfulPostStr(String serviceUrl, String jsonStr) throws ZiRuntimeException {
        ResultData resultData = restfulPost(serviceUrl, jsonStr);
        if (!resultData.isSucceed()) {
            log.error("调用服务返回false,case:" + resultData.getErrorMsg() + "URL:" + serviceUrl + " param:" + jsonStr);
            throw new ZiRuntimeException(resultData.getErrorCode(), resultData.getErrorMsg(), "");
        }
        if (Objects.isNull(resultData.getData())) {
            log.warn("调用服务返回null,result:" + resultData.getData());
            return null;
        }
        return String.valueOf(resultData.getData());
    }

}
