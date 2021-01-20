package com.ziyi.common.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 基于HttpUrlConnection实现的http请求工具类
 */
public class HttpUrlConnectionUtils {

    private static final Logger logger = LoggerFactory.getLogger(HttpUrlConnectionUtils.class);

    //请求头
    private Map<String, String> headers = new ConcurrentHashMap<>();

    private int connectionTimeout;
    private int readTimeout;

    private String requestEncode = "UTF-8";
    private String responseEncoe = "UTF-8";

    /**
     * 获得一个连接对象
     * @return HttpUrlConnectionUtils
     */
    public static HttpUrlConnectionUtils create(){
        return new HttpUrlConnectionUtils();
    }

    /**
     * 初始化连接对象
     */
    protected HttpUrlConnectionUtils(){
        this.connectionTimeout = HttpConstant.REQUEST_CONNECT_TIME_OUT_DEFAULT;
        this.readTimeout = HttpConstant.REQUEST_READ_TIME_OUT_DEFAULT;
        this.headers.put(HttpConstant.REQUEST_CONTENT_TYPE, HttpConstant.REQUEST_CONTENT_DEFAULT);
    }

    /**
     * 连接对象添加自定义请求头
     * @param name 请求头名
     * @param value 请求头值
     * @return
     */
    public HttpUrlConnectionUtils addHeader(String name, String value){
        this.headers.put(name, value);
        return this;
    }

    /**
     * 移除连接对象的某个请求头
     * @param name 请求头名
     * @return
     */
    public HttpUrlConnectionUtils removeHeader(String name){
        this.headers.remove(name);
        return this;
    }

    /**
     * 设置连接对象contentType
     * @param contentType
     * @return
     */
    public HttpUrlConnectionUtils setContentType(String contentType){
        this.headers.put(HttpConstant.REQUEST_CONTENT_TYPE, contentType);
        return this;
    }

    /**
     * 设置连接超时时间
     * @param connectionTimeout
     * @return
     */
    public HttpUrlConnectionUtils setConnectTimeout(int connectionTimeout){
        this.connectionTimeout = connectionTimeout;
        return this;
    }

    /**
     * 设置读超时时间
     * @param readTimeout
     * @return
     */
    public HttpUrlConnectionUtils setReadTimeout(int readTimeout){
        this.readTimeout = readTimeout;
        return this;
    }

    /**
     * 设置连接请求的编码格式
     * @param encode
     * @return
     */
    public HttpUrlConnectionUtils setRequestEncode(String encode){
        this.requestEncode = encode;
        return this;
    }

    /**
     * 设置返回参数的编码格式
     * @param requestEncode
     * @return
     */
    public HttpUrlConnectionUtils setResponseEncode(String requestEncode){
        this.responseEncoe = requestEncode;
        return this;
    }

    /**
     * post请求方式
     * @param url 请求url
     * @param data 请求参数
     * @return
     * @throws Exception
     */
    public String post(String url, String data) throws Exception{
        return post(url, null, data);
    }

    /**
     * post请求方式
     * @param url 请求url
     * @param data 请求参数为byte数组
     * @return
     * @throws Exception
     */
    public byte[] post(String url, byte[] data) throws Exception {
        return post(url, null, data);
    }

    /**
     * post请求方式，返回参数为string
     * @param url 请求url
     * @param queryParams url后跟着的参数
     * @param data 表单参数
     * @return
     * @throws Exception
     */
    public String post(String url, Map<String, String> queryParams, String data) throws Exception {
        if (queryParams != null && queryParams.size() > 0){
            try {
                url = HttpUrlConnectionUtils.encodeRequestParam(url, queryParams, requestEncode);
            } catch (Exception e){
                logger.error("send post request error , wrong request encoding:{}", requestEncode, e);
                throw  new Exception("wrong request encoding", e);
            }
        }
        return NativeHttpClient.post(url, data, headers, requestEncode, responseEncoe, connectionTimeout, readTimeout);
    }

    /**
     * post请求方式,返回参数为byte
     * @param url
     * @param queryParams
     * @param data
     * @return
     * @throws Exception
     */
    public byte[] post(String url, Map<String, String> queryParams, byte[] data) throws Exception {
        if (queryParams != null && queryParams.size() > 0){
            try {
                url = HttpUrlConnectionUtils.encodeRequestParam(url, queryParams, requestEncode);
            } catch (Exception e){
                logger.error("send post request error , wrong request encoding:{}", requestEncode, e);
                throw  new Exception("wrong request encoding", e);
            }
        }
        return NativeHttpClient.post(url, data, headers, requestEncode, responseEncoe, connectionTimeout, readTimeout);
    }

    /**
     * get请求
     * @param url 请求url
     * @param params 请求参数（会encode）
     * @return
     * @throws Exception
     */
    public String get(String url, Map<String, String> params) throws Exception {
        String getUrl;
        try {
            getUrl = encodeRequestParam(url, params, responseEncoe);
        } catch (Exception e) {
            logger.error("send get request error, wrong request encoding:{}", requestEncode, e);
            throw new Exception("wrong request encoding", e);
        }

        return NativeHttpClient.get(url, headers, responseEncoe, connectionTimeout, readTimeout);
    }

    /**
     * get请求方式，请求参数包含在url中，参数需要自己encode
     * @param url
     * @return
     * @throws Exception
     */
    public String  get(String url) throws Exception {
        return NativeHttpClient.get(url, headers, responseEncoe, connectionTimeout, readTimeout);
    }

    /**
     * 对get请求参数进行encoe
     * @param url 请求url
     * @param paramMap 请求参数
     * @param encode encode编码格式
     * @return 拼接好的url
     * @throws UnsupportedEncodingException
     */
    protected static String encodeRequestParam(String url, Map<String, String> paramMap, String encode) throws UnsupportedEncodingException {
        String result = "";
        StringBuilder urlBuilder = new StringBuilder(url);

        if (paramMap.size() > 0){
            urlBuilder.append("?");
            for (Map.Entry<String, String> entry : paramMap.entrySet()){
                urlBuilder.append(entry.getKey());
                urlBuilder.append("=");
                urlBuilder.append(URLEncoder.encode(entry.getValue(), encode));
                urlBuilder.append("&");
            }
            result = urlBuilder.toString();
            if (result.endsWith("&")) {
                result = result.substring(0, result.length()-1);
            }
        }
        return result;
    }
}
