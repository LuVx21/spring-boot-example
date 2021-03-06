package org.luvx.modules.data.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName: org.luvx.module.generator.entity
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/4/1 20:08
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ColumnDO {
    private String dbName;
    private String tableName;
    private String columnName;
    private String dataType;
    private String comment;

    /**
     * 对应类名
     */
    private String className;
    /**
     * 对应对象名
     */
    private String objectName;
}


