package com.ziyi.Schedule;

import com.ziyi.common.date.DateTimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TestScheduleForSimple {

    private static final Logger logger = LoggerFactory.getLogger(TestScheduleForSimple.class);

    //@Scheduled(cron = "0 */1 * * * ?")
    private void process(){
        if (logger.isDebugEnabled()) {
            logger.debug("Schedule success");
            System.out.println("TestScheduleForSimple Schedule , Now Date:"+ DateTimeUtils.nowDate());
        }
    }

}
