package com.ziyi.Schedule;

import com.ziyi.common.date.DateTimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


/**
 * 定时任务测试类
 */
@Component
public class TestSchedule {
    private static final Logger logger = LoggerFactory.getLogger(TestSchedule.class);

    public void work(){
        if (logger.isDebugEnabled()) {
            logger.debug("Schedule success");
            System.out.println("Test Schedule , Now Date:"+ DateTimeUtils.nowDate());
        }
    }

}
