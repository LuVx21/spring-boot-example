package org.luvx.module.data.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName: org.luvx.module.generator.entity
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/4/1 20:07
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TableDO {
    private String         dbName;
    private String         tableName;
    /**
     * 不支持联合主键
     */
    private ColumnDO       pk;
    private List<ColumnDO> columns;
    private String         comment;

    /**
     * 对应类名
     */
    private String className;
    /**
     * 对应对象名
     */
    private String objectName;
}
