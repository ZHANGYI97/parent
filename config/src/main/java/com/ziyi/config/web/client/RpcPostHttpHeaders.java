package com.ziyi.config.web.client;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

/**
 * @author zhy
 * @date 2022/7/3
 */
public class RpcPostHttpHeaders extends HttpHeaders {

    public RpcPostHttpHeaders() {
        this.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
    }
}

