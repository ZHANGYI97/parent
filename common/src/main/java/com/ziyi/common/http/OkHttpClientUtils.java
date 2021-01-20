package com.ziyi.common.http;

import com.ziyi.common.base.BaseException;
import com.ziyi.common.string.StringUtils;
import okhttp3.*;
import okhttp3.logging.HttpLoggingInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**、
 * 基于okhttpClient实现的http工具类
 */
public class OkHttpClientUtils {

    private static final Logger logger = LoggerFactory.getLogger(OkHttpClientUtils.class);

    public static final int CONNECT_TIMEOUT = 60;

    public static final int READ_TIMEOUT = 100;

    public static final int WRITE_TIMEOUT = 100;

    public static final int RETRY_TIME = 5;

    protected static OkHttpClient defaultClient = new OkHttpClient().newBuilder()
            .addInterceptor(getLogInterceptor())
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS).readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS).build();

    protected static Map<String, OkHttpClient> proxyClient = new HashMap<>();

    protected OkHttpClient client;

    protected String userAgent;

    protected OkHttpClientUtils(OkHttpClient client) {
        this.client = client;
        this.userAgent = null;
    }

    /**
     * 获取一个工具类对象
     * @return
     */
    public static OkHttpClientUtils get(){
        return new OkHttpClientUtils(getOkHttpClientUtils());
    }

    /**
     * 获取一个工具类对象（使用代理通讯）
     * @param proxy
     * @param port
     * @return
     */
    public static OkHttpClientUtils get(String proxy, int port) {
        return new OkHttpClientUtils(getOkHttpClientUtils(proxy, port));
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getUserAgent(){
        return this.userAgent;
    }

    /**
     * 获取代理连接
     * @param proxy
     * @param port
     * @return
     */
    protected static OkHttpClient getOkHttpClientUtils(String proxy, int port) {
        String clientName = proxy + ":" + port;
        OkHttpClient client = proxyClient.get(clientName);
        if (client != null) {
            return client;
        }
        synchronized (OkHttpClientUtils.class) {
            client = proxyClient.get(proxy + ":" + port);
            if (client == null) {
                logger.debug("创建OkHttpClient,代理服务器：proxyIP={},proxyPort={}", proxy, port);
                client = getOkHttpClientUtils().newBuilder()
                        .proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxy, port))).build();
                proxyClient.put(clientName, client);
            }
        }
        return client;
    }

    /**
     * 根据系统阐述配置，判断访问url是否需要用代理连接
     * @return
     */
    protected static OkHttpClient getOkHttpClientUtils(){
        return defaultClient;
    }

    public static Interceptor getLogInterceptor(){
        //自带日志拦截器，使用jdk自带的日志
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(
                meaasge -> {
                    if (logger.isDebugEnabled()) {
                        logger.debug(meaasge);
                    }
                }
        );
        logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        return logging;
    }

    /**
     * 发送get请求
     * @param url 请求地址
     * @param params 请求参数，需自行encode
     * @param headers http请求头，可为空
     * @return
     * @throws BaseException
     */
    public HttpResponse<String> get(String url, Map<String, String> params,
                                    Map<String, String> headers) throws BaseException {
        if (url == null) {
            throw new IllegalArgumentException("url should not be null");
        }
        String requestUrl = buildUrlWithRequestParams(url, params);
        try {
            Response response = request(requestUrl, HttpConstant.REQUEST_METHOD_GET, headers, null);
            HttpResponse<String> httpResponse = parseHttpResponseHeaders(response);
            httpResponse.setResponseBody(response.body().string());
            response.close();
            return httpResponse;
        } catch (IOException e) {
            logger.error("get request for {} failed", requestUrl, e);
            throw new BaseException("send http get request error.", e.getMessage());
        }
    }

    /**
     * 发送delete请求
     * @param url 请求url
     * @param params 请求参数，需自行encode
     * @param headers http请求头，可为空
     * @return
     * @throws BaseException
     */
    public HttpResponse<String> delete(String url, Map<String, String> params,
                                       Map<String, String> headers) throws BaseException{
        if (url == null) {
            throw new IllegalArgumentException("url should not be null");
        }
        String requestUrl = buildUrlWithRequestParams(url, params);
        try {
            Response response = request(requestUrl, HttpConstant.REQUEST_METHOD_DELETE, headers, null);
            HttpResponse<String> httpResponse = parseHttpResponseHeaders(response);
            httpResponse.setResponseBody(response.body().string());
            response.close();
            return httpResponse;
        } catch (IOException e){
            logger.error("get request for {} failed", requestUrl, e);
            throw new BaseException("send http get request error.", e.getMessage());
        }
    }

    /**
     * post请求
     * @param url 请求url
     * @param headers 请求头 可空
     * @param contentType 请求内容类型 不可空
     * @param content 请求体内容
     * @return
     * @throws BaseException
     */
    public HttpResponse<String> post(String url, Map<String, String> headers,
                                       String contentType, String content) throws BaseException{
        if (url == null) {
            throw new IllegalArgumentException("url should not be null");
        }
        if (contentType == null) {
            throw new IllegalArgumentException("contentType should not be null");
        }

        RequestBody body = RequestBody.create(MediaType.parse(contentType), content);
        Response response = request(url, HttpConstant.REQUEST_METHOD_POST, headers, body);
        try {
            HttpResponse<String> httpResponse = parseHttpResponseHeaders(response);
            httpResponse.setResponseBody(response.body().string());
            response.close();
            return httpResponse;
        } catch (IOException e){
            logger.error("get request for {} failed", url, e);
            throw new BaseException("send http get request error.", e.getMessage());
        }
    }

    /**
     * put请求
     * @param resUrl 请求url
     * @param headers 请求头 可空
     * @param contenttype 请求内容类型 不可空
     * @param content 请求体内容
     * @return
     * @throws BaseException
     */
    public HttpResponse<String> put(String resUrl, Map<String, String> headers,
                                     String contenttype, String content) throws BaseException{
        if (resUrl == null) {
            throw new IllegalArgumentException("resUrl should not be null");
        }
        if (contenttype == null) {
            throw new IllegalArgumentException("contentType should not be null");
        }

        RequestBody body = RequestBody.create(MediaType.parse(contenttype), content);
        Response response = request(resUrl, HttpConstant.REQUEST_METHOD_PUT, headers, body);
        try {
            HttpResponse<String> httpResponse = parseHttpResponseHeaders(response);
            httpResponse.setResponseBody(response.body().string());
            response.close();
            return httpResponse;
        } catch (IOException e){
            logger.error("get request for {} failed", resUrl, e);
            throw new BaseException("send http get request error.", e.getMessage());
        }
    }

    protected <T> HttpResponse<T> parseHttpResponseHeaders(Response response) throws IOException {
        HttpResponse<T> resp = new HttpResponse<>();
        ResponseBody responseBody = response.body();

        resp.setStatus(response.code());
        resp.setHearders(response.headers().toMultimap());
        resp.setContentLength(responseBody.contentLength());
        resp.setContentType(responseBody.contentType());

        return resp;
    }

    /**
     * 发送http请求，处理结束后，应关闭response
     * 不建议使用
     * @param url 服务器地址
     * @param method 请求方式
     * @param headers 请求头，可为null
     * @param requestBody 请求头，可为null
     * @return
     * @throws BaseException
     */
    @Deprecated
    public Response request(String url, String method,
                             Map<String, String> headers, RequestBody requestBody) throws BaseException {
        if (url == null) {
            throw new IllegalArgumentException("url should not be null.");
        }
        if (method == null) {
            throw new IllegalArgumentException("method should not be null.");
        }

        Response response = null;
        try {
            Request.Builder reqBuild = new Request.Builder();
            setHeaders(headers, reqBuild);
            Request request = reqBuild.url(url).method(method, requestBody).build();
            System.out.println("==========="+request.header("User-Agent"));
            response = this.client.newCall(request).execute();
            return response;
        } catch (IOException e) {
            logger.error("request for {} failed", url, e);
            if (response != null) {
                response.close();
            }
            throw new BaseException("send http request error",e.getMessage());
        }
    }

    /**
     * 设置请求头
     * @param headers
     * @param reqBuild
     */
    private void setHeaders(Map<String, String> headers, Request.Builder reqBuild) {
        if (headers != null) {
            if (!StringUtils.isNullOrEmpty(this.userAgent)) {
                reqBuild.addHeader("User-Agent", this.userAgent);
            }
            reqBuild.headers(Headers.of(headers));
        } else {
            if (!StringUtils.isNullOrEmpty(this.userAgent)) {
                reqBuild.addHeader("User-Agent", this.userAgent);
            }
        }
    }

    /**
     * 请求带有参数的url地址，注意，此方法不会encode
     * @param url
     * @param params
     * @return
     */
    public static String buildUrlWithRequestParams(String url, Map<String, String> params) {
        String query = "";
        if (params != null && params.size() > 0) {
            query = "?";
            for (Map.Entry<String, String> entry : params.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                query = query + key + "=" + value + "&";
            }
            if (query.endsWith("&")) {
                query = query.substring(0, query.length() - 1);
            }
        }
        return url + query;
    }

    public static class HttpResponse<T> {
        Map<String, List<String>> hearders;

        T responseBody;

        long contentLength;

        MediaType contentType;

        int status;

        public Map<String, List<String>> getHearders() {
            return hearders;
        }

        public void setHearders(Map<String, List<String>> hearders) {
            this.hearders = hearders;
        }

        public T getResponseBody() {
            return responseBody;
        }

        public void setResponseBody(T responseBody) {
            this.responseBody = responseBody;
        }

        public long getContentLength() {
            return contentLength;
        }

        public void setContentLength(long contentLength) {
            this.contentLength = contentLength;
        }

        public MediaType getContentType() {
            return contentType;
        }

        public void setContentType(MediaType contentType) {
            this.contentType = contentType;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        @Override
        public String toString() {
            return "HttpResponse{" +
                    "hearders=" + hearders +
                    ", responseBody=" + responseBody +
                    ", contentLength=" + contentLength +
                    ", contentType=" + contentType +
                    ", status=" + status +
                    '}';
        }
    }

}
