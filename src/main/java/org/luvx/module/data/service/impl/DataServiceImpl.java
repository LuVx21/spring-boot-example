package org.luvx.module.data.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.luvx.module.data.entity.ColumnDO;
import org.luvx.module.data.entity.TableDO;
import org.luvx.module.data.mapper.DataMapper;
import org.luvx.module.data.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName: org.luvx.module.data.service.impl
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/4/12 17:26
 */
@Slf4j
@Service
public class DataServiceImpl implements DataService {
    @Autowired
    private DataMapper dataMapper;

    @Override
    public List<TableDO> getAllTable() {
        return dataMapper.getAllTable();
    }

    @Override
    public TableDO getTable(String dbName, String tableName) {
        return dataMapper.getTable(dbName, tableName);
    }

    @Override
    public List<ColumnDO> getColumns(String dbName, String tableName) {
        return dataMapper.getColumns(dbName, tableName);
    }
}
