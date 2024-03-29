package com.ziyi.config.web.handler;

import cn.hutool.core.util.ArrayUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.ziyi.api.domain.entity.MongoEntity;
import com.ziyi.common.date.DateUtils;
import com.ziyi.common.enums.ServletTypeEnum;
import com.ziyi.mongo.service.ConfigMongoDBService;
import io.swagger.annotations.ApiOperation;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.lang.Nullable;
import org.springframework.util.ClassUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author zhy
 * @date 2022/7/3
 */
@Order(-1)
@Slf4j
@Aspect
public class ZiWebServletLogHandler {

    private static final List<Class<?>> LOG_IGNORE_CLASS = Lists.newArrayList(HttpServletRequest.class,
            ServletRequest.class, HttpServletResponse.class, ServletResponse.class);

    private static final boolean SWAGGER_PRESENT = ClassUtils
            .isPresent("io.swagger.annotations.ApiOperation", ZiWebServletLogHandler.class.getClassLoader());

    private static final String X_FORWARDED_FOR = "x-forwarded-for";

    private static final String PROXY_CLIENT_IP = "Proxy-Client-IP";

    private static final String WL_PROXY_CLIENT_IP = "WL-Proxy-Client-IP";

    private static final String UNKNOWN = "unknown";

    @Autowired
    ConfigMongoDBService configMongoDBService;

    /**
     * create a pointcut
     */
    @Pointcut("execution(* com.ziyi..controller.*.*(..))")
    private void method() {}

    @Around("method()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        HttpServletRequest request = Optional.ofNullable(RequestContextHolder.getRequestAttributes())
                .filter(ServletRequestAttributes.class::isInstance).map(ServletRequestAttributes.class::cast)
                .map(ServletRequestAttributes::getRequest).orElse(null);
        if (log.isInfoEnabled()) {
            log.info("------------------------------------------------------------------------------------VVV");
            if (null != request) {
                log.info("////  request ip[请求来源客户端IP]>>>>  [{}:{}]", getIp(request), request.getRemotePort());
                log.info("////  request url[请求路径]>>>>  [{}]", request.getRequestURL());
            } else {
                log.info("////  request method[请求方法]>>>>  [{}:{}]",
                        point.getSignature().getDeclaringType().getSimpleName(),
                        point.getSignature().getName());
            }
            if (SWAGGER_PRESENT) {
                String operationName = SwaggerApiOperationNameFactory.getSwaggerApiOperationName(point);
                if (null != operationName) {
                    log.info("////  request method name[方法名称]>>>>   [{}]", operationName);
                }
            }
            if (ArrayUtil.isNotEmpty(point.getArgs())) {
                log.info("////  request param[请求入参]>>>>");
                String argsStr = Arrays.stream(point.getArgs())
                        .filter(obj -> LOG_IGNORE_CLASS.stream().noneMatch(clazz -> clazz.isAssignableFrom(obj.getClass())))
                        .map(arg -> arg instanceof String ? (String) arg : JSON.toJSONString(arg))
                        .collect(Collectors.joining(", "));
                log.info("{}", argsStr);
                Object[] reqArr = new Object[point.getArgs().length];
                for (int i=0;i < point.getArgs().length;i++ ) {
                    if (!(point.getArgs()[i] instanceof ServletRequest)) {
                        reqArr[i] = point.getArgs()[i];
                    }
                }
                //todo 存储入参
                saveLog(ServletTypeEnum.REQUET, request.getRequestURI(), 0, reqArr);
            }
        }
        long startTime = System.currentTimeMillis();
        Object obj = point.proceed();
        if (log.isInfoEnabled()) {
            long endTime = System.currentTimeMillis();
            log.info("////  request time[耗时]>>>>  [{}] ms, request response[请求返参]:{}", (endTime - startTime),
                    JSON.toJSONString(obj));
            log.info("------------------------------------------------------------------------------------^^^\n\n");
            //todo 存储反参
        }
        saveLog(ServletTypeEnum.RESPONSE, request.getRequestURI(), 0, obj);
        return obj;
    }

    private void saveLog(ServletTypeEnum servletTypeEnum, String requetMethod, long timeConsuming, Object data){
        String traceId = MDC.get("traceId");
        String servletType = servletTypeEnum.name();
        String methodName = requetMethod.replaceAll("/", ".");
        if (StringUtils.isNotEmpty(methodName) && methodName.startsWith(".")) {
            methodName = methodName.substring(1);
        }
        configMongoDBService.save(new MongoEntity<>(traceId, methodName, servletType, data, timeConsuming, DateUtils.getTime()), methodName);
    }

    private static String getIp(HttpServletRequest request) {
        if (null == request) {
            return "";
        }
        String ip = request.getHeader(X_FORWARDED_FOR);
        if (ip == null || ip.length() == 0 || PROXY_CLIENT_IP.equalsIgnoreCase(ip)) {
            ip = request.getHeader(PROXY_CLIENT_IP);
        }
        if (ip == null || ip.length() == 0 || WL_PROXY_CLIENT_IP.equalsIgnoreCase(ip)) {
            ip = request.getHeader(WL_PROXY_CLIENT_IP);
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    private static  <T extends Annotation> T getAnnotation(JoinPoint point, Class<T> annotationClass) {
        return Optional.ofNullable(getMethod(point)).map(m -> m.getAnnotation(annotationClass)).orElse(null);
    }

    /**
     * 获取切点方法
     *
     * @param point 切点
     * @return 注解
     */
    private static Method getMethod(JoinPoint point) {
        return Optional.ofNullable(point.getSignature()).filter(MethodSignature.class::isInstance)
                .map(MethodSignature.class::cast).map(MethodSignature::getMethod).orElse(null);
    }

    /**
     * Inner class to avoid hard-coded Swagger dependency.
     */
    @UtilityClass
    private class SwaggerApiOperationNameFactory {

        @Nullable
        public String getSwaggerApiOperationName(JoinPoint point) {
            return Optional.ofNullable(getAnnotation(point, ApiOperation.class)).map(ApiOperation::value)
                    .filter(org.springframework.util.StringUtils::hasText).orElse(null);
        }
    }
}
