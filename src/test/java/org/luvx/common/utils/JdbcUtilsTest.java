package org.luvx.common.utils;

import org.junit.Test;
import org.luvx.common.annotation.JdbcInfoAnnotation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @ClassName: org.luvx.common.utils
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/4/11 17:04
 */
public class JdbcUtilsTest {

    @Test
    @JdbcInfoAnnotation(url = "jdbc:mysql://127.0.0.1/boot")
    public void getConnectionTest() throws SQLException {
        Connection conn = JdbcUtils.getConnectionFromCache(null, null, null);
        String sql = "select * from user;";

        // Statement stmt = conn.createStatement();
        // ResultSet rs = stmt.executeQuery(sql);

        PreparedStatement stmt = JdbcUtils.getStatement(conn, sql);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            String userName = rs.getString(2);
            String password = rs.getString(3);
            int age = rs.getInt(4);
            System.out.println(userName + ":" + password + ":" + age);
        }

        // JdbcUtils.release(rs, stmt, null);
        JdbcUtils.close(rs);
    }
}