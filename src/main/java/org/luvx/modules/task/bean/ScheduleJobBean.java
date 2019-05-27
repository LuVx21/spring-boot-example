package org.luvx.modules.task.bean;

import com.google.common.base.Stopwatch;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.luvx.common.utils.ApplicationContextUtils;
import org.luvx.modules.task.entity.ScheduleTaskEntity;
import org.luvx.modules.task.entity.ScheduleTaskLogEntity;
import org.luvx.modules.task.service.ScheduleTaskLogService;
import org.luvx.modules.task.utils.ScheduleConst;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: org.luvx.modules.task.utils
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/5/24 18:21
 */
@Slf4j
public class ScheduleJobBean extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        ScheduleTaskEntity taskEntity = (ScheduleTaskEntity) context.getMergedJobDataMap()
                .get(ScheduleConst.JOB_KEY);

        ScheduleTaskLogService scheduleTaskLogService = ApplicationContextUtils.getBean(ScheduleTaskLogService.class);

        ScheduleTaskLogEntity logEntity = new ScheduleTaskLogEntity();
        logEntity.setJobId(taskEntity.getJobId());
        logEntity.setBeanName(taskEntity.getBeanName());
        logEntity.setParams(taskEntity.getParams());
        logEntity.setCreateTime(LocalDateTime.now());

        Stopwatch stopwatch = Stopwatch.createStarted();
        try {
            Object target = ApplicationContextUtils.getBean(taskEntity.getBeanName());
            Method method = target.getClass().getDeclaredMethod("run", String.class);
            method.invoke(target, taskEntity.getParams());

            long nanos = stopwatch.elapsed(TimeUnit.MILLISECONDS);
            logEntity.setDuration(nanos);
            logEntity.setStatus(0);
        } catch (Exception e) {
            long nanos = stopwatch.elapsed(TimeUnit.MILLISECONDS);
            logEntity.setDuration(nanos);
            logEntity.setStatus(1);
            logEntity.setError(StringUtils.substring(e.toString(), 0, 2000));

        } finally {
            scheduleTaskLogService.save(logEntity);
        }
    }
}
