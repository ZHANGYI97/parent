package com.ziyi.common.base.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 类 编 号：
 * 类 名 称：SerializeAsSting
 * 内容摘要：Long类型转String (js接受JSON数据丢失)
 * 创建日期：2022/7/3
 *
 * @author zhy
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(
        {ElementType.FIELD})
public @interface SerializeAsSting {
}
