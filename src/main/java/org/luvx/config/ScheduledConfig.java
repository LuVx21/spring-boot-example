package org.luvx.config;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;


/**
 * @ClassName: org.luvx.common.config
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/4/2 15:38
 */
@Slf4j
@EnableScheduling
@Configuration
public class ScheduledConfig implements SchedulingConfigurer {

    private static final int size = 10;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(setExecutor());
    }

    @Bean
    public ScheduledExecutorService setExecutor() {
        ScheduledExecutorService service =
                new ScheduledThreadPoolExecutor(size, new ThreadFactoryBuilder().setNameFormat("schedule-executor-%d").build());

        return service;
    }
}