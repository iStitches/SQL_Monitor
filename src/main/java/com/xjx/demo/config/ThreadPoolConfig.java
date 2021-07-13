package com.xjx.demo.config;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

@Configuration
public class ThreadPoolConfig {
    @Bean(value = "threadPoolInstance")
    public ExecutorService createThreadPoolInstance(){
        // 通过guava类库实现线程工厂并设置名称
        ThreadFactory factory = new ThreadFactoryBuilder().setNameFormat("thread-pool-%d").build();
        ExecutorService threadPool = new ThreadPoolExecutor(10,16,60L, TimeUnit.SECONDS,new ArrayBlockingQueue<Runnable>(100),new ThreadPoolExecutor.AbortPolicy());
        return threadPool;
    }
}
