package org.luvx.module.user.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.luvx.common.annotation.LogAnnotation;
import org.luvx.common.annotation.MeasurementAnnotation;
import org.luvx.module.common.Response;
import org.luvx.module.user.entity.User;
import org.luvx.module.user.pojo.model.UserModel;
import org.luvx.module.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName: org.luvx.module.user.controller
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/3/8 17:26
 */
@Api(value = "UserController", description = "用户", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService service;

    @ApiOperation(value = "新建用户", notes = "根据User对象创建用户")
    @ApiImplicitParam(name = "user", value = "用户实体user", required = true, dataType = "User")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String insertUser(@RequestBody User user) {
        int userId = service.insertUser(user);
        return userId + "";
    }

    @ApiOperation(value = "删除指定用户", notes = "删除指定用户")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long", paramType = "Long")
    @RequestMapping(value = {"/delete/{id}"}, method = RequestMethod.DELETE)
    public String deleteUserById(@PathVariable Long id) {
        int num = service.deleteUserById(id);
        return num + "";
    }

    @ApiOperation(value = "更新指定用户", notes = "根据url的id来指定更新对象，并根据传过来的user信息来更新用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long", paramType = "Long"),
            @ApiImplicitParam(name = "user", value = "用户实体", required = true, dataType = "User")
    })
    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public String updateUser(@PathVariable Long id, @RequestBody User user) {
        int num = service.updateUser(id, user);
        return num + "";
    }

    @ApiOperation(value = "获取指定用户信息", notes = "获取指定用户信息")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long", paramType = "Long")
    @MeasurementAnnotation
    @LogAnnotation("获取用户")
    @RequestMapping(value = {"/{id}"}, method = RequestMethod.GET)
    public String getUserById(@PathVariable Long id) {
        User user = service.getUserById(id);
        R<User> r = Response.ok(user);
        return JSON.toJSONString(r);
    }

    @ApiOperation(value = "获取全部用户信息", notes = "获取全部用户信息")
    @ApiImplicitParam(name = "userModel", value = "用户model", required = true)
    @MeasurementAnnotation
    @LogAnnotation("获取全部用户")
    @RequestMapping(value = {"/select"}, method = RequestMethod.POST)
    public String selectUsers(@RequestBody UserModel userModel) {
        IPage<User> page = service.selectUsers(userModel);
        R<List<User>> r = Response.ok(page.getRecords());
        return JSON.toJSONString(r);
    }
}
