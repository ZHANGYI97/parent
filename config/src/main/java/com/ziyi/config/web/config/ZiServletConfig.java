package com.ziyi.config.web.config;

import com.sun.tools.internal.xjc.outline.Aspect;
import com.ziyi.common.base.AbstractZiException;
import com.ziyi.common.constants.MsgCodeConstants;
import com.ziyi.common.constants.PriorityConstants;
import com.ziyi.common.model.ResultData;
import com.ziyi.config.web.client.SpringCloudClient;
import com.ziyi.config.web.handler.HandlerUtil;
import com.ziyi.config.web.handler.ZiWebServletLogHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.UndeclaredThrowableException;

/**
 *  全局异常处理
 *
 * @author zhy
 * @date 2022/7/3
 */
@Configuration
@ConditionalOnClass(HttpServletRequest.class)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class ZiServletConfig {

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean(RestTemplate.class)
    public SpringCloudClient springCloudClient(RestTemplate restTemplate) {
        return new SpringCloudClient(restTemplate);
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnClass(Aspect.class)
    public ZiWebServletLogHandler apecWebLogHandler() {
        return new ZiWebServletLogHandler();
    }

    /**
     * znw global exception Handler
     * catch all exception and convert to ResultData
     *
     * @author zhangyx
     * @date 2019/9/25 17:00
     * @since 2.0.0
     *
     * @since 3.0
     * support reactive
     * @author chenzx
     * @date 2021/6/4
     */
    @Slf4j
    @RestControllerAdvice
    @Order(PriorityConstants.HIGHEST_PRIORITY_ORDER)
    @ConditionalOnClass({RestControllerAdvice.class, HttpServletRequest.class})
    public static class ErrHandler {

        @ExceptionHandler(value = UndeclaredThrowableException.class)
        public ResultData<Void> handleUndeclaredThrowableException(HttpServletRequest request,
                                                                   UndeclaredThrowableException e) {
            if (e.getUndeclaredThrowable() instanceof AbstractZiException) {
                return handleApecException(request, (AbstractZiException) e.getUndeclaredThrowable());
            }

            if (e.getUndeclaredThrowable() instanceof BindException) {
                return handleBindException(request, (BindException) e.getUndeclaredThrowable());
            }
            return handleException(request, e);
        }

        @ExceptionHandler(value = AbstractZiException.class)
        public ResultData<Void> handleApecException(HttpServletRequest request, AbstractZiException e) {
            ResultData<Void> result = HandlerUtil.getExceptionResultData(e.getErrorCode(), e.getErrorMsg());
            log.error("//// catch a【{}】 request url :{}, errorCode:{},errorMsg:{},errorDetail:{},case by ", e.getClass().getSimpleName(),
                    request.getRequestURI(), e.getErrorCode(), result.getErrorMsg(), e.getErrorDetail(), e);
            return result;
        }

        @ExceptionHandler(value = {BindException.class})
        public ResultData<Void> handleBindException(HttpServletRequest request, BindException e) {
            ResultData<Void> result = HandlerUtil.getBindExceptionResultData(e);
            log.error("//// catch a【{}】 request url :{}, errorCode:{},errorMsg:{}", e.getClass().getSimpleName(),
                    request.getRequestURI(), result.getErrorCode(), result.getErrorMsg(), e);
            return result;
        }

        @ExceptionHandler(value = Exception.class)
        public ResultData<Void> handleException(HttpServletRequest request, Exception e) {
            //因为有客户端在用，优化下提示
            ResultData<Void> result = HandlerUtil.getExceptionResultData(MsgCodeConstants.SYSTEM_ERROR, "网络异常，请稍后重试！");
            log.error("//// catch a【{}】 request url :{},errorMsg:{},case by ", e.getClass().getSimpleName(),
                    request.getRequestURI(), result.getErrorMsg(), e);
            return result;
        }
    }
}
