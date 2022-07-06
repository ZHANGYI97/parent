package com.ziyi.config.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * auther:jurzis
 * date: 2021/2/24 10:37
 */
@Configuration
@EnableAsync
public class ExecutorConfig {

    private static Logger logger = LoggerFactory.getLogger(ExecutorConfig.class);

    @Value("${thread-pool.default.core-pool-size:50}")
    int corePoolSize;

    @Value("${thread-pool.default.max-pool-size:100}")
    int maxPoolSize;

    @Value("${thread-pool.default.queue-capacity:50}")
    int queueCapacity;

    @Value("${thread-pool.default.keep-alive-seconds:60}")
    int keepAliveSeconds;

    @Value("${thread-pool.default.thread-name-prefix:thread-}")
    String threadNamePrefix;

    @Bean("asyncServiceExecutor")
    public Executor asyncServiceExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //配置核心线程数
        executor.setCorePoolSize(this.corePoolSize);
        //配置最大线程数
        executor.setMaxPoolSize(this.maxPoolSize);
        //配置队列大小
        executor.setQueueCapacity(this.queueCapacity);
        //配置线程池中的线程的名称前缀
        executor.setThreadNamePrefix(this.threadNamePrefix);
        if (this.keepAliveSeconds > 0){
            executor.setAllowCoreThreadTimeOut(true);
            executor.setKeepAliveSeconds(this.keepAliveSeconds);
        }
        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //执行初始化
        executor.initialize();
        return executor;
    }

}
