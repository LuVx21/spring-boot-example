package org.luvx.modules.task.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("app_schedule_task")
public class ScheduleTaskEntity {
    @TableId
    private Long          jobId;
    private String        beanName;
    private String        params;
    private String        cronExpr;
    private Integer       status;
    private String        descr;
    private LocalDateTime createTime;
    private String        createUser;
    private LocalDateTime updateTime;
    private String        updateUser;
}
