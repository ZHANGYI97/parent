package com.ziyi.common.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Set;

public class NativeHttpClient {

    private static final Logger logger = LoggerFactory.getLogger(NativeHttpClient.class);

    /**
     * 发送post请求，返回参数为string
     * @param url
     * @param data
     * @param headers
     * @param requestEncode
     * @param responseEncode
     * @param connectionTimeout
     * @param readTimeout
     * @return
     * @throws Exception
     */
    protected static String post(String url,
                                 String data,
                                 Map<String, String> headers,
                                 String requestEncode,
                                 String responseEncode,
                                 int connectionTimeout,
                                 int readTimeout) throws Exception{
        PrintWriter output = null;
        String response = null;
        HttpURLConnection conn = null;
        try {
            URL reqUrl = new URL(url);
            conn = (HttpURLConnection) reqUrl.openConnection();
            conn.setConnectTimeout(connectionTimeout);
            conn.setReadTimeout(readTimeout);

            conn.setRequestMethod(HttpConstant.REQUEST_METHOD_POST);
            conn = setHeaderProperty(conn, headers);
            conn.setDoInput(true);
            conn.setDoOutput(true);

            output = new PrintWriter(new OutputStreamWriter(conn.getOutputStream(), requestEncode));
            output.write(data);
            output.flush();

            if (logger.isDebugEnabled()){
                logger.debug("HTTP Request param:{}", data);
                logger.debug("HTTP Response code:{}", conn.getResponseCode());
            }

            response = readResponse(conn, requestEncode);
            if (logger.isDebugEnabled()){
                logger.debug("HTTP Response:{}", response);
            }
            return response;
        } catch (Exception e) {
            logger.error("发送请求失败，请求的url:{},错误信息为：{}", url, e);
            throw new Exception("send get resquest error.", e);
        } finally {
            IoUtils.closeQuietly(output);
            if (conn != null){
                conn.disconnect();
            }
        }
    }

    /**
     * 发送post请求，返回参数为byte[]
     * @param url
     * @param data
     * @param headers
     * @param requestEncode
     * @param responseEncode
     * @param connectionTimeout
     * @param readTimeout
     * @return
     * @throws Exception
     */
    protected static byte[] post(String url,
                                 byte[] data,
                                 Map<String, String> headers,
                                 String requestEncode,
                                 String responseEncode,
                                 int connectionTimeout,
                                 int readTimeout) throws Exception{
        OutputStream output = null;
        byte[] response = null;
        HttpURLConnection conn = null;
        try {
            URL reqUrl = new URL(url);
            conn = (HttpURLConnection) reqUrl.openConnection();
            conn.setConnectTimeout(connectionTimeout);
            conn.setReadTimeout(readTimeout);

            conn.setRequestMethod(HttpConstant.REQUEST_METHOD_POST);
            conn = setHeaderProperty(conn, headers);
            conn.setDoInput(true);
            conn.setDoOutput(true);

            output = conn.getOutputStream();
            output.write(data);
            output.flush();

            if (logger.isDebugEnabled()){
                logger.debug("HTTP Request param:{}", data);
                logger.debug("HTTP Response code:{}", conn.getResponseCode());
            }

            response = readResponseBytes(conn);
            if (logger.isDebugEnabled()){
                logger.debug("HTTP Response:{}", response);
            }
            return response;
        } catch (Exception e) {
            logger.error("发送请求失败，请求的url:{},错误信息为：{}", url, e);
            throw new Exception("send get resquest error.", e);
        } finally {
            IoUtils.closeQuietly(output);
            if (conn != null){
                conn.disconnect();
            }
        }
    }

    /**
     * get请求，返回参数为string
     * @param url
     * @param headers
     * @param responseEncode
     * @param connectTimeout
     * @param readTimeout
     * @return
     * @throws Exception
     */
    protected static String get(String url, Map<String, String> headers, String responseEncode,
                                int connectTimeout, int readTimeout) throws Exception {
        HttpURLConnection connection = null;
        try {
           URL reqUrl = new URL(url);
           connection = (HttpURLConnection) reqUrl.openConnection();
           connection.setConnectTimeout(connectTimeout);
           connection.setReadTimeout(readTimeout);

           connection.setRequestMethod(HttpConstant.REQUEST_METHOD_GET);

           connection = setHeaderProperty(connection, headers);
            if (logger.isDebugEnabled()){
                logger.debug("HTTP Request url:{}", url);
                logger.debug("HTTP Response code:{}", connection.getResponseCode());
            }

            String response = readResponse(connection, responseEncode);
            if (logger.isDebugEnabled()){
                logger.debug("HTTP Response:{}", response);
            }
            return response;
        } catch (Exception e) {
            logger.error("发送请求失败，请求url：{}，错误信息为：{}", url, e.getMessage(), e);
            throw new Exception("send get request error", e);
        } finally {
            if (connection != null){
                connection.disconnect();
            }
        }
    }

    /**
     * 设置连接请求头
     * @param conn
     * @param headers
     * @return
     */
    protected static HttpURLConnection setHeaderProperty(HttpURLConnection conn, Map<String, String> headers) {
        Set<Map.Entry<String, String>> entrySet = headers.entrySet();
        for (Map.Entry<String, String> entry : entrySet) {
            conn.setRequestProperty(entry.getKey(), entry.getValue());
        }
        return conn;
    }

    /**
     * 获取返回数据字符
     * @param connection
     * @return
     * @throws Exception
     */
    private static String readResponse(HttpURLConnection connection, String encoding) throws Exception{
        InputStream inputStream = null;
        try {
            inputStream = connection.getInputStream();
            byte[] data = IoUtils.readToEnd(inputStream);
            return new String(data, encoding);
        } finally {
            IoUtils.closeQuietly(inputStream);
        }
    }

    /**
     * 获取返回数据字节数组
     * @param connection
     * @return
     * @throws Exception
     */
    private static byte[] readResponseBytes(HttpURLConnection connection) throws Exception{
        InputStream inputStream = null;
        try {
            inputStream = connection.getInputStream();
            return IoUtils.readToEnd(inputStream);
        } finally {
            IoUtils.closeQuietly(inputStream);
        }
    }

}
