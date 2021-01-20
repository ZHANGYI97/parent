package com.ziyi.util;

import com.ziyi.base.Response;
import com.ziyi.common.string.StringUtils;
import com.ziyi.config.BeanFactory;
import com.ziyi.entity.Schedule.ScheduleJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 定时任务工具类
 */
@Component
public class TaskUtils {

    private static Logger logger = LoggerFactory.getLogger(TaskUtils.class);

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * 通过反射调用scheduleJob中定义的方法
     *
     * @param scheduleJob
     */
    public static void invokMethod(ScheduleJob scheduleJob) {

        try {//添加最大的异常捕获
            String springId = scheduleJob.getSpringId();
            Object object = null;
            Class clazz = null;

            //根据反射来进行
            if (!StringUtils.isNullOrEmpty(springId)) {
                object = BeanFactory.getBean(springId);
            }

            if (object == null && !StringUtils.isNullOrEmpty(scheduleJob.getBeanClass())) {
                //根据springid没有得到需要执行的类，再根据全路径名获取
                String jobStr = "Schedule nam : [" + scheduleJob.getJobName() + "]-spring not springId,  class type Acquiring...";
                if (logger.isDebugEnabled()){
                    logger.debug(jobStr, scheduleJob.getBeanClass());
                }
                try {
                    clazz = Class.forName(scheduleJob.getBeanClass());
                    object = BeanFactory.getBean(clazz);
                    if(object == null){
                        //全路径也没有获取到，根据类的类型获取
                        jobStr = "Schedule name: [" + scheduleJob.getJobName() + "]- spring not get bean, add spring and building ...";
                        if (logger.isDebugEnabled()){
                            logger.debug(jobStr, scheduleJob.getBeanClass());
                        }
                        object = BeanFactory.getBeanByType(clazz);
                    }
                    if (!StringUtils.isNullOrEmpty(springId)) {
                        BeanFactory.setBean(springId, object);
                        if (logger.isDebugEnabled()) {
                            logger.debug("spring bean build Successful and setting beanFactory", scheduleJob.getBeanClass());
                        }
                    }
                    if (logger.isDebugEnabled()) {
                        logger.debug("Schedule spring bean build Successful! ", scheduleJob.getBeanClass());
                    }
                } catch (Exception e) {
                    logger.error("定时任务 spring bean build failed !!! ", scheduleJob.getBeanClass(), e);
                    Response.newResponse().error(e);
                    return;
                }
            }

            clazz = object.getClass();
            Method method = null;
            try {
                method = clazz.getDeclaredMethod(scheduleJob.getMethodName());
            } catch (NoSuchMethodException e) {
                String jobStr = "Schedule name is : [" + scheduleJob.getJobName() + "] = start failed，method name setting error！";
                logger.error(jobStr, e);
            } catch (SecurityException e) {
                logger.error("TaskUtils Exception", e);
                Response.newResponse().error(e);
            }
            if (method != null) {
                try {
                    method.invoke(object);
                    if (logger.isDebugEnabled()) {
                        logger.debug("Schedule name is : [" + scheduleJob.getJobName() + "] start Successful");
                    }
                } catch (Exception e) {
                    Response.newResponse().error(e);
                    logger.error("Schedule name is : [" + scheduleJob.getJobName() + "] start failed!", e);
                    return;
                }
            } else {
                String jobStr = "Schedule name is : [" + scheduleJob.getJobName() + "] start failed!";
                logger.error(jobStr, clazz.getName(), "not find method ");
            }

        } catch (Exception e) {//添加最大的异常捕获
            Response.newResponse().error(e);
            logger.error("Schedule name is :[" + scheduleJob.getJobName() + "]  start failed!", e);
        }

    }


}
