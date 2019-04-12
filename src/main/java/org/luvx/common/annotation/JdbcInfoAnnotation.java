package org.luvx.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName: org.luvx.common.annotation
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/4/11 19:37
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface JdbcInfoAnnotation {
    String driverClass() default "com.mysql.cj.jdbc.Driver";

    String url() default "jdbc:mysql://localhost:3306/test";

    String userName() default "root";

    String password() default "1121";
}
