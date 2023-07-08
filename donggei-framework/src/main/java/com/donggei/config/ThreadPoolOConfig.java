package com.donggei.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor; //和这个是有区别的

/**
 * @className: ThreadPoolOConfig
 * @description: TODO 类描述
 * @author: Dong
 * @date: 2022/9/28
 **/
@Configuration
@EnableAsync //开启多线程
public class ThreadPoolOConfig {

    @Bean("taskExecutor")
    public Executor asyncServiceExecutor(){

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(Integer.MAX_VALUE);
        executor.setThreadGroupName("donggei.top");
        //设置所有任务结束后关闭线程池
        executor.setWaitForTasksToCompleteOnShutdown(true);
        //执行初始化
        executor.initialize();
        return executor;
    }

}
