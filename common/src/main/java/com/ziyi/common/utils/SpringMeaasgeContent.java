package com.ziyi.common.utils;

import org.springframework.context.ApplicationContext;

import java.util.Locale;

/**
 * 类 编 号：
 * 类 名 称：SpringMeaasgeContent
 * 内容摘要：Spring上下文获取
 *
 * @author zhy
 * @date 2022/7/3
 */

public abstract class SpringMeaasgeContent {
    private static ApplicationContext applicationContext;

    public static void setApplicationContext(ApplicationContext applicationContext) {
        SpringMeaasgeContent.applicationContext = applicationContext;
    }

    /**
     * @return 获取applicationContext
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * @param name bean的名字
     * @return 指定的bean
     */
    public static Object getBean(String name) {
        return applicationContext.getBean(name);
    }

    /**
     * @param code ：对应messages配置的key
     * @return 对应messages配置的value
     */
    public static String getMessage(String code) {
        return getMessage(code, new Object[]{});
    }

    /**
     * @param code           对应messages配置的key
     * @param defaultMessage 没有设置key的时候的默认值
     * @return 对应messages配置的value
     */
    public static String getMessage(String code, String defaultMessage) {
        return getMessage(code, null, defaultMessage);
    }

    /**
     * @param code           对应messages配置的key
     * @param defaultMessage 没有设置key的时候的默认值
     * @param locale         所使用的语言
     * @return 对应messages配置的value
     */
    public static String getMessage(String code, String defaultMessage, Locale locale) {
        return getMessage(code, null, defaultMessage, locale);
    }

    /**
     * @param code   对应messages配置的key
     * @param locale 所使用的语言
     * @return 对应messages配置的value
     */
    public static String getMessage(String code, Locale locale) {
        return getMessage(code, null, "", locale);
    }

    /**
     * @param code ：对应messages配置的key
     * @param args : 数组参数
     * @return 对应messages配置的value
     */
    public static String getMessage(String code, Object... args) {
        return getMessage(code, args, "");
    }

    /**
     * 为了避免Long型号被拆分逗号分开，所有数值占位字段都强制换成string
     *
     * @param code ：对应messages配置的key
     * @param args : 数组参数
     * @return 对应messages配置的value
     */
    public static String getStringMessage(String code, Object... args) {
        if (args != null && args.length > 0) {
            int len = args.length;
            for (int i = 0; i < len; i++) {
                Object arg = args[i];
                if (arg instanceof Number) {
                    args[i] = String.valueOf(arg);
                }
            }
        }

        return getMessage(code, args, "");
    }

    /**
     * @param code   对应messages配置的key
     * @param args   数组参数
     * @param locale 所有使用的语言
     * @return code对应的值
     */
    public static String getMessage(String code, Object[] args, Locale locale) {
        return getMessage(code, args, "", locale);
    }

    /**
     * @param code           ：对应messages配置的key
     * @param args           : 数组参数
     * @param defaultMessage : 没有设置key的时候的默认值
     * @return 对应messages配置的value
     */
    private static String getMessage(String code, Object[] args, String defaultMessage) {
        //这里使用比较方便的方法，不依赖request.
//        Locale locale = LocaleContextHolder.getLocale();
//        if(!Locale.CHINA.equals( LocaleContextHolder.getLocale() ))
//        {
//            return getMessage( code, args, defaultMessage, Locale.CHINA );
//        }
        return getMessage(code, args, defaultMessage, Locale.CHINA);

    }

    /**
     * 指定语言
     *
     * @param code           对应messages配置的key
     * @param args           数组参数
     * @param defaultMessage 没有设置key的时候的默认值.
     * @param locale         所有使用的语言
     * @return 对应messages配置的value
     */
    private static String getMessage(String code, Object[] args, String defaultMessage, Locale locale) {
        return applicationContext.getMessage(code, args, defaultMessage, locale);
    }

}
