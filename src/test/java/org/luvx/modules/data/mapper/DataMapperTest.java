package org.luvx.modules.data.mapper;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.luvx.base.BaseTest;
import org.luvx.modules.data.entity.ColumnDO;
import org.luvx.modules.data.entity.TableDO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @ClassName: org.luvx.module.data.mapper
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/4/1 20:27
 */
public class DataMapperTest extends BaseTest {

    @Autowired
    DataMapper mapper;

    @Test
    public void listTables() {
        List<TableDO> list = mapper.getAllTable();
        System.out.println(JSON.toJSONString(list));
    }

    @Test
    public void getTableTest() {
        TableDO table = mapper.getTable("boot", "user");
        System.out.println(JSON.toJSONString(table));
    }

    @Test
    public void getPkTest() {
        ColumnDO pk = mapper.getPk("boot", "user");
        System.out.println(pk);
    }

    @Test
    public void getColumnsTest() {
        List<ColumnDO> columnList = mapper.getColumns("boot", "user");
        System.out.println(columnList);
    }

}