package org.luvx.modules.task.utils;

import lombok.extern.slf4j.Slf4j;
import org.luvx.modules.task.bean.ScheduleJobBean;
import org.luvx.modules.task.entity.ScheduleTaskEntity;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @ClassName: org.luvx.modules.task.utils
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/5/24 18:21
 */
@Slf4j
@Component
public class ScheduleUtils {
    private final static String JOB_NAME = "TASK_";

    private static Scheduler scheduler;

    @Autowired
    public void setScheduler(Scheduler scheduler) {
        ScheduleUtils.scheduler = scheduler;
    }

    /**
     * 获取触发器key
     */
    public static TriggerKey getTriggerKey(Long jobId) {
        return TriggerKey.triggerKey(JOB_NAME + jobId);
    }

    /**
     * 获取jobKey
     */
    public static JobKey getJobKey(Long jobId) {
        return JobKey.jobKey(JOB_NAME + jobId);
    }

    /**
     * 获取表达式触发器
     */
    public static CronTrigger getCronTrigger(Long jobId) {
        try {
            return (CronTrigger) scheduler.getTrigger(getTriggerKey(jobId));
        } catch (SchedulerException e) {
            throw new RuntimeException("获取定时任务CronTrigger出现异常", e);
        }
    }

    /**
     * 创建定时任务
     */
    public static void createScheduleJob(ScheduleTaskEntity entity) {
        try {
            //构建job信息
            JobDetail jobDetail = JobBuilder.newJob(ScheduleJobBean.class).withIdentity(getJobKey(entity.getJobId())).build();

            //表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(entity.getCronExpr())
                    .withMisfireHandlingInstructionDoNothing();

            //按新的cronExpression表达式构建一个新的trigger
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(getTriggerKey(entity.getJobId())).withSchedule(scheduleBuilder).build();

            //放入参数，运行时的方法可以获取
            jobDetail.getJobDataMap().put(ScheduleConst.JOB_KEY, entity);

            scheduler.scheduleJob(jobDetail, trigger);

            //暂停任务
            if (entity.getStatus() == ScheduleConst.ScheduleStatus.PAUSE.getValue()) {
                pauseJob(entity.getJobId());
            }
        } catch (SchedulerException e) {
            throw new RuntimeException("创建定时任务失败", e);
        }
    }

    /**
     * 更新定时任务
     */
    public static void updateScheduleJob(ScheduleTaskEntity scheduleJob) {
        try {
            TriggerKey triggerKey = getTriggerKey(scheduleJob.getJobId());

            //表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpr())
                    .withMisfireHandlingInstructionDoNothing();

            CronTrigger trigger = getCronTrigger(scheduleJob.getJobId());

            //按新的cronExpression表达式重新构建trigger
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

            //参数
            trigger.getJobDataMap().put(ScheduleConst.JOB_KEY, scheduleJob);

            scheduler.rescheduleJob(triggerKey, trigger);

            //暂停任务
            if (scheduleJob.getStatus() == ScheduleConst.ScheduleStatus.PAUSE.getValue()) {
                pauseJob(scheduleJob.getJobId());
            }

        } catch (SchedulerException e) {
            throw new RuntimeException("更新定时任务失败", e);
        }
    }

    /**
     * 立即执行任务
     */
    public static void runJobNow(ScheduleTaskEntity scheduleJob) {
        try {
            //参数
            JobDataMap dataMap = new JobDataMap();
            dataMap.put(ScheduleConst.JOB_KEY, scheduleJob);

            scheduler.triggerJob(getJobKey(scheduleJob.getJobId()), dataMap);
        } catch (SchedulerException e) {
            throw new RuntimeException("立即执行定时任务失败", e);
        }
    }

    /**
     * 暂停任务
     */
    public static void pauseJob(Long jobId) {
        try {
            scheduler.pauseJob(getJobKey(jobId));
        } catch (SchedulerException e) {
            throw new RuntimeException("暂停定时任务失败", e);
        }
    }

    /**
     * 恢复任务
     */
    public static void resumeJob(Long jobId) {
        try {
            scheduler.resumeJob(getJobKey(jobId));
        } catch (SchedulerException e) {
            throw new RuntimeException("暂停定时任务失败", e);
        }
    }

    /**
     * 删除定时任务
     */
    public static void deleteJob(Long jobId) {
        try {
            scheduler.deleteJob(getJobKey(jobId));
        } catch (SchedulerException e) {
            throw new RuntimeException("删除定时任务失败", e);
        }
    }
}
