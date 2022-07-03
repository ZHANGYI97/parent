package com.ziyi;

import com.ziyi.elasticsearch.config.listener.Log4j2PropertiesEventListener;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@MapperScan(value = "com.ziyi.mapper")
@EnableScheduling
@SpringBootApplication
public class AdminApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(AdminApplication.class);
        // 添加Log4j2配置文件监听
        Log4j2PropertiesEventListener eventListener = new Log4j2PropertiesEventListener();
        application.addListeners(eventListener);
        application.run(args);
    }

}
