package org.luvx.common.task;


import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @ClassName: org.luvx.common.properties
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/3/26 11:12
 */
@Component
public class Task {
    @Async
    @Scheduled(cron = "0 0 * * * ?")
    public void execute() {
        System.out.println("1" + System.currentTimeMillis());
    }

    @Async
    @Scheduled(cron = "0 30 * * * ?")
    public void execute1() {
        System.out.println("2" + System.currentTimeMillis());
    }
}
