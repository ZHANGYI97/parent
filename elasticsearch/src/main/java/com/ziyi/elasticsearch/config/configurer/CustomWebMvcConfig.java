package com.ziyi.elasticsearch.config.configurer;

import com.ziyi.elasticsearch.config.interceptor.RequestTraceIdInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author zhy
 * @data 2022/6/27 22:26
 */
@Configuration
public class CustomWebMvcConfig implements WebMvcConfigurer {

    final RequestTraceIdInterceptor requestTraceIdInterceptor;

    @Autowired
    public CustomWebMvcConfig(RequestTraceIdInterceptor requestTraceIdInterceptor) {
        this.requestTraceIdInterceptor = requestTraceIdInterceptor;
    }

    /**
     * 添加拦截器
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 添加TraceId拦截器
        registry.addInterceptor(requestTraceIdInterceptor);
    }
}
