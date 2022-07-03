package com.ziyi.elasticsearch.config.interceptor;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @author zhy
 * @data 2022/6/27 22:25
 */
@Component
public class RequestTraceIdInterceptor implements HandlerInterceptor {
    /**
     * 日志TRACE_ID名称常量
     */
    private static final String LOG_TRACE_ID_NAME = "traceId";

    /**
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        MDC.put(LOG_TRACE_ID_NAME, UUID.randomUUID().toString().replace("-",""));
        return true;
    }

}
