package com.ziyi.common.base;

import com.ziyi.common.enums.ErrorEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

/**
 * 公共返回结果对象
 */
public class Response extends HashMap<String, Object> {

    //返回码
    private final static String CODE = "code";

    //返回对象
    private final static String DATA = "data";

    //成功数
    private final static String TOTAL = "total";

    //响应描述信息
    private final static String MESSAGE = "message";


    private Logger logger = LoggerFactory.getLogger(Response.class);

    private Response() {
        this.put(CODE, ErrorEnum.SUCCESS.getCode());
        this.put(MESSAGE, ErrorEnum.SUCCESS.getMessage());
    }

    /**
     * 返回一个成功对象
     * @return
     */
    public static Response newResponse() {
        return new Response();
    }

    /**
     * 对返回对象设置成功数及数据
     * @param total
     * @param rows
     * @return
     */
    public static Response set(Integer total, Object rows) {
        Response response = new Response();
        response.setTotal(total);
        response.ok(rows);
        return response;
    }

    /**
     * 获取一个返回对象并对返回对象设置返回data
     * @param rows
     * @return
     */
    public static Response set(Object rows) {
        Response response = new Response();
        response.ok(rows);
        return response;
    }

    /**
     * 对返回对象添加一对响应参数
     * @param key
     * @param value
     * @return
     */
    public static Response set(String key, Object value) {
        Response response = Response.newResponse();
        response.put(key, value);
        return response;
    }

    /**
     * 对返回对象设置返回data
     * @param data
     */
    public Response setData(Object data){
        this.put(DATA, data);
        return this;
    }

    /**
     * 对返回对象添加一对响应参数
     * @param key
     * @param value
     * @return
     */
    public Response put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    /**
     * 获得错误消息
     */
    public String getMessage() {
        Object msg = this.get(MESSAGE);
        return msg != null ? msg.toString() : null;
    }

    /**
     * 对返回对象中某一对响应值的key进行更换
     * @param fromKey 老key
     * @param toKey 新key
     * @return
     */
    public Response moveTo(String fromKey, String toKey) {
        Object val = this.get(fromKey);
        this.put(toKey, val);
        this.remove(fromKey);
        return this;
    }

    /**
     * 判断响应是否是成功
     * @return
     */
    public Boolean isOK() {
        return this.getCode() == ErrorEnum.SUCCESS.getCode();
    }

    /**
     * 判断响应是否是失败
     * @return
     */
    public Boolean isFail() {
        return !isOK();
    }

    /**
     * 返回一个成功的返回对象
     * @param data
     * @return
     */
    public Response ok(Object data) {
        super.put(DATA, data);
        return this;
    }

    /**
     * 返回一个成功的返回对象，并设置一对需要的返回键值对
     * @param key
     * @param val
     * @return
     */
    public Response ok(String key, Object val) {
        super.put(key, val);
        return this;
    }

    /**
     * 返回一个成功的返回对象，并设置成功条数和需要返回的对象
     * @param count
     * @param data
     * @return
     */
    public Response setResults(Integer count, Object data) {
        this.setTotal(count);
        this.ok(data);
        return this;
    }

    /**
     * 设置返回code
     * @param code
     * @return
     */
    private Response setCode(int code) {
        this.put(CODE, code);
        return this;
    }

    /**
     * 设置返回码与返回信息
     * 强列建议在ErrorEnum 中定义错误, 再调用setError()方法
     * @param code
     * @param message
     * @return
     */
    public Response setCodeAndMessage(int code, String message) {
        this.put(CODE, code);
        this.put(MESSAGE, message);
        return this;
    }


    /**
     * 设置返回信息
     * 此方法只能用于标明一些已有错误code,但需要特别定制的返回消息时用到
     * 请不要滥用此方法
     * @param message
     * @return
     */
    public Response setMessage(String message) {
        this.put(MESSAGE, message);
        return this;
    }

    public int getCode() {
        return Integer.parseInt(this.get(CODE).toString());
    }


    public Response setTotal(Integer total) {
        this.put(TOTAL, total);
        return this;
    }

    public Response OK() {
        this.setCode(ErrorEnum.SUCCESS.getCode());
        this.put(MESSAGE, "ok");
        return this;
    }

    /**
     * 设置错误类型
     * 传入ErrorEnum 枚举
     */
    public Response setError(ErrorEnum errorEnum){
        this.setCode(errorEnum.getCode());
        this.setMessage(errorEnum.getMessage());
        return this;
    }

    /**
     * 	打印异常栈信息,
     * 	记录异常日志,
     * 	返回错误代码到前台.
     * @param e
     * @return
     */
    public Response error(Throwable e) {
        //打印异常栈信息到控制台
        e.printStackTrace();
        //记录异常日志
        logger.error("ExceptionError",e);

        //返回错误代码到前台
        this.setCode(ErrorEnum.SERVER_ERROR.getCode());
        this.put(MESSAGE, ErrorEnum.SERVER_ERROR.getMessage());

        return this;
    }

}
