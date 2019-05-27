package org.luvx.modules.task.controller;


import com.baomidou.mybatisplus.extension.api.R;
import org.luvx.modules.common.Response;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/aaa")
public class ScheduleTaskController {

    @RequestMapping(value = "/select/{configKey}", method = RequestMethod.GET)
    public R<?> selectAppConfig(@PathVariable String configKey) {
        return Response.ok("");
    }
}
