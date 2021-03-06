package org.luvx.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName: org.luvx.common.config
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/3/8 17:49
 */
@MapperScan("org.luvx.modules.**.mapper")
@Configuration
public class MybatisConfig {
}
