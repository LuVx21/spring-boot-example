package org.luvx.common.utils;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalNotification;
import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.luvx.common.annotation.JdbcInfoAnnotation;

import java.lang.reflect.Method;
import java.sql.*;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: org.luvx.common.utils
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/3/28 11:33
 */
@Slf4j
public class JdbcUtils {

    private static final String args = "?useUnicode=true&characterEncoding=utf-8&serverTimezone=UTC&useSSL=true&allowMultiQueries=true&autoReconnect=true";

    /**
     * 数据库连接缓存
     */
    public static Cache<String, Connection> connCache = CacheBuilder.newBuilder()
            .maximumSize(512)
            .expireAfterAccess(1, TimeUnit.HOURS)
            .removalListener(
                    (RemovalNotification<String, Connection> notification) -> close(notification.getValue())
            )
            .build(
                    // CacheLoader.from((key) ->
                    //         getConnection("", "", "")
                    // )
            );

    public static Cache<String, PreparedStatement> stmtCache = CacheBuilder.newBuilder()
            .maximumSize(512)
            .expireAfterAccess(1, TimeUnit.HOURS)
            .removalListener(
                    (RemovalNotification<String, PreparedStatement> notification) -> close(notification.getValue())
            )
            .build();

    /*
    static {
        try {
            Class.forName(driverClass);
        } catch (ClassNotFoundException e) {
            throw new ExceptionInInitializerError("加载驱动失败");
        }
    }
    */

    /**
     * 获取数据库连接
     *
     * @param url
     * @param username
     * @param password
     * @return
     * @throws RuntimeException
     * @throws SQLException
     */
    public static Connection getConnectionFromCache(String url, String username, String password)
            throws RuntimeException, SQLException {
        if (StringUtils.isBlank(url)) {
            Map<String, String> info = getAnnotationInfo();
            url = info.get("url");
            username = info.get("user");
            password = info.get("password");
        }

        String key = url + "@" + username;
        Connection conn = connCache.getIfPresent(key);
        if (conn == null
                || (conn != null && conn.isClosed())
        ) {
            conn = getConnection(url, username, password);
            connCache.put(key, conn);
        }

        return conn;
    }

    private static Connection getConnection(String url, String username, String password) {
        log.info("获取数据库连接:{} 用户:{}", url, username);
        try {
            return DriverManager.getConnection(url + args, username, password);
        } catch (SQLException e) {
            log.error("获取数据库连接:{} 用户:{}", url, username);
            throw new RuntimeException("获取数据库连接失败, 请检查配置");
        }
    }

    /**
     * 获取PreparedStatement
     *
     * @param conn
     * @param sql
     * @return
     * @throws RuntimeException
     * @throws SQLException
     */
    public static PreparedStatement getStatement(Connection conn, String sql)
            throws RuntimeException, SQLException {
        Objects.requireNonNull(conn, "数据库连接不可为空");
        if (StringUtils.isBlank(sql)) {
            throw new RuntimeException("sql语句不可为空");
        }

        PreparedStatement stmt = stmtCache.getIfPresent(sql);
        if (stmt == null
                || (stmt != null && stmt.isClosed())) {
            log.info("创建PreparedStatement");
            stmt = conn.prepareStatement(sql);
            stmtCache.put(sql, stmt);
        }

        return stmt;
    }

    /**
     * 释放资源
     *
     * @param autoCloseable
     */
    public static void close(AutoCloseable autoCloseable) {
        if (autoCloseable != null) {
            try {
                autoCloseable.close();
            } catch (Exception e) {
                log.error("释放资源失败", e);
            }
            autoCloseable = null;
        }
    }

    /**
     * 释放所有资源
     *
     * @param rs
     * @param stmt
     * @param conn
     */
    public static void release(ResultSet rs, Statement stmt, Connection conn) {
        close(rs);
        close(stmt);
        close(conn);
    }

    /**
     * 得到注解中配置的jdbc信息
     *
     * @return
     */
    private static Map<String, String> getAnnotationInfo() {
        final int i = 3;
        /// StackTraceElement[] stacks = new Throwable().getStackTrace();
        StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
        StackTraceElement stack = stacks[i];

        Class clazz;
        Method method = null;
        try {
            clazz = Class.forName(stack.getClassName());
            method = clazz.getDeclaredMethod(stack.getMethodName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        JdbcInfoAnnotation jdbcInfoAnnotation = method.getAnnotation(JdbcInfoAnnotation.class);
        Objects.requireNonNull(jdbcInfoAnnotation, "没有指定的注解(JdbcInfoAnnotation)");

        Map<String, String> info = ImmutableMap.of(
                "drive", jdbcInfoAnnotation.driverClass(),
                "url", jdbcInfoAnnotation.url(),
                "user", jdbcInfoAnnotation.userName(),
                "password", jdbcInfoAnnotation.password()
        );
        return info;
    }
}
