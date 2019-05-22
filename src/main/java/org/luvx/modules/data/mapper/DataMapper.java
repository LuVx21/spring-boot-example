package org.luvx.modules.data.mapper;

import org.apache.ibatis.annotations.Param;
import org.luvx.modules.data.entity.ColumnDO;
import org.luvx.modules.data.entity.TableDO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: org.luvx.module.data.mapper
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/4/1 20:22
 */
@Repository
public interface DataMapper {
    /**
     * 取得当前库中的所有的表
     *
     * @return
     */
    List<TableDO> getAllTable();

    /**
     * 获取指定表的表信息
     *
     * @param dbName
     * @param tableName
     * @return
     */
    TableDO getTable(@Param("dbName") String dbName, @Param("tableName") String tableName);

    /**
     * 获取表的主键
     *
     * @param dbName
     * @param tableName
     * @return
     */
    ColumnDO getPk(@Param("dbName") String dbName, @Param("tableName") String tableName);

    /**
     * 获取指定表的列信息
     *
     * @param dbName
     * @param tableName
     * @return
     */
    List<ColumnDO> getColumns(@Param("dbName") String dbName, @Param("tableName") String tableName);
}
