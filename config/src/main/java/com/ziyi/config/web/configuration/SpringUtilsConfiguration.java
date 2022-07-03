package com.ziyi.config.web.configuration;

import com.ziyi.common.utils.SpringMeaasgeContent;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;

/**
 * 向 SpringUtils 注入应用上下文
 *
 * @author zhy
 * @date 2022/7/3
 */
@Configuration
@ConditionalOnClass(SpringMeaasgeContent.class)
public class SpringUtilsConfiguration implements ApplicationContextAware {

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        SpringMeaasgeContent.setApplicationContext(applicationContext);
    }
}
