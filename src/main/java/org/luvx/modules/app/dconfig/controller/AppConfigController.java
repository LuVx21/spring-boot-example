package org.luvx.modules.app.dconfig.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.luvx.modules.app.dconfig.entity.AppConfig;
import org.luvx.modules.app.dconfig.service.AppConfigService;
import org.luvx.modules.common.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * @ClassName: org.luvx.modules.app.dconfig.controller
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/3/31 17:26
 */
@Api(value = "AppConfigController", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@RestController
@RequestMapping(value = "/config")
public class AppConfigController {
    @Autowired
    private AppConfigService appConfigService;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public R<AppConfig> saveAppConfig(@RequestBody AppConfig appConfig) {
        appConfigService.save(appConfig);
        return Response.ok(appConfig);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public R<AppConfig> deleteAppConfig(@RequestBody AppConfig appConfig) {
        if (StringUtils.isBlank(appConfig.getConfigId())) {
            return Response.failed("配置id不可为空");
        }

        // 逻辑删除
        appConfig.setValid(false);
        appConfigService.updateById(appConfig);

        return Response.ok(appConfig);
    }

    @RequestMapping(value = "/delete/{configId}", method = RequestMethod.DELETE)
    public R<Boolean> deleteAppConfig(@PathVariable String configId) {
        Objects.requireNonNull(configId, "配置id不可为空");
        int i = 2 / 0;
        /// 物理删除
        boolean flag = appConfigService.removeById(configId);
        return Response.ok(flag);
    }


    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public R<AppConfig> updateAppConfig(@RequestBody AppConfig appConfig) {
        appConfigService.update(appConfig,
                new QueryWrapper<AppConfig>().eq("config_key", appConfig.getConfigKey())
        );
        return Response.ok(appConfig);
    }

    @RequestMapping(value = "/select/{configKey}", method = RequestMethod.GET)
    public R<AppConfig> selectAppConfig(@PathVariable String configKey) {
        AppConfig appConfig = appConfigService.getOne(
                new QueryWrapper<AppConfig>().eq("config_key", configKey)
        );

        return Response.ok(appConfig);
    }
}
