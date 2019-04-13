package org.luvx.module.data.service;

import org.luvx.module.data.entity.ColumnDO;
import org.luvx.module.data.entity.TableDO;

import java.util.List;

/**
 * @ClassName: org.luvx.module.data.service
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/4/12 17:26
 */
public interface DataService {

    List<TableDO> getAllTable();

    /**
     * 获取指定表的表信息
     *
     * @param dbName
     * @param tableName
     * @return
     */
    TableDO getTable(String dbName, String tableName);

    /**
     * 获取指定表的列信息
     *
     * @param dbName
     * @param tableName
     * @return
     */
    List<ColumnDO> getColumns(String dbName, String tableName);
}
