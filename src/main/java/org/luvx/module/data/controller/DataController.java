package org.luvx.module.data.controller;

import lombok.extern.slf4j.Slf4j;
import org.luvx.module.data.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: org.luvx.module.data.controller
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/4/12 17:28
 */
@Slf4j
@RestController
@RequestMapping(value = "/user")
public class DataController {

    @Autowired
    private DataService dataService;
}
