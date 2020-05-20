package com.excmmy.controller;


import com.excmmy.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import pojo.MallConstant;
import pojo.ResponseJsonBody;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Excmmy
 * @since 2020-05-17
 */
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @ApiOperation("Users服务GET测试接口")
    @GetMapping(value = "/hello") // 任意请求
    public ResponseJsonBody hello1() {
        ResponseJsonBody responseJsonBody = new ResponseJsonBody();
        responseJsonBody.setCode(MallConstant.SUCCESS_CODE);
        responseJsonBody.setMsg("Users服务GET测试接口");
        return responseJsonBody;
    }

    @ApiOperation("Users服务POST测试接口")
    @PostMapping(value = "/hello") // 任意请求
    public ResponseJsonBody hello2() {
        ResponseJsonBody responseJsonBody = new ResponseJsonBody();
        responseJsonBody.setCode(MallConstant.SUCCESS_CODE);
        responseJsonBody.setMsg("Users服务POST测试接口");
        return responseJsonBody;
    }

    @ApiOperation("新建用户接口")
    @PostMapping(value = "/insert/user") // 任意请求
    public ResponseJsonBody insertUser(
            @RequestParam(name = "username")String username,
            @RequestParam(name = "userpass")String userpass) {
        return userService.insertUser(username, userpass);
    }
}

