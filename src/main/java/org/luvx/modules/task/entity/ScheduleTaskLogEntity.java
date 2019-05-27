package org.luvx.modules.task.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@TableName("app_schedule_task_log")
public class ScheduleTaskLogEntity {
    @TableId
    private Long          logId;
    @NotBlank
    private Long          jobId;
    private String        beanName;
    private String        params;
    private Integer       status;
    private String        error;
    private Long          duration;
    private LocalDateTime createTime;
    private String        createUser;
    private LocalDateTime updateTime;
    private String        updateUser;
}
