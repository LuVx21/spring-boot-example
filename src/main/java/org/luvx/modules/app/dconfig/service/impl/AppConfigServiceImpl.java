package org.luvx.modules.app.dconfig.service.impl;

import org.luvx.base.BaseServiceImpl;
import org.luvx.modules.app.dconfig.entity.AppConfig;
import org.luvx.modules.app.dconfig.mapper.AppConfigMapper;
import org.luvx.modules.app.dconfig.service.AppConfigService;
import org.springframework.stereotype.Service;

/**
 * @ClassName: org.luvx.module.app.service.impl
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/3/8 17:34
 */
@Service
public class AppConfigServiceImpl extends BaseServiceImpl<AppConfigMapper, AppConfig>
        implements AppConfigService {
}
