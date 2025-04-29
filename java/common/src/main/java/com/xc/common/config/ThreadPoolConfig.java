package com.xc.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class ThreadPoolConfig {

    // CPU个数
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();

    @Bean("taskExecutor")
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        // 核心线程数
        taskExecutor.setCorePoolSize(CPU_COUNT * 2);
        // 最大线程数
        taskExecutor.setMaxPoolSize(CPU_COUNT * 4);
        // 队列大小
        taskExecutor.setQueueCapacity(CPU_COUNT * 16);
        // 线程空闲保持时间
        taskExecutor.setKeepAliveSeconds(60);
        // 线程名前缀
        taskExecutor.setThreadNamePrefix("FreeMaker-");
        // 等待所有任务结束后再关闭线程池
        taskExecutor.setAllowCoreThreadTimeOut(true);
        // 拒绝策略
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

        taskExecutor.initialize();
        return taskExecutor;
    }
}
