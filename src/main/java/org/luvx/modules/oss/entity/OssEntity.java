package org.luvx.modules.oss.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @ClassName: org.luvx.modules.oss.entity
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/5/23 10:29
 */
@Data
@Builder
@TableName("oss_file")
public class OssEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    @TableId
    private Long          id;
    /**
     *
     */
    private String        url;
    /**
     *
     */
    private LocalDateTime createDate;
}
