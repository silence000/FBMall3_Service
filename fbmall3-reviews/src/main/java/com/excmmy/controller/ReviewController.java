package com.excmmy.controller;


import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import pojo.MallConstant;
import pojo.ResponseJsonBody;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Excmmy
 * @since 2020-05-24
 */
@RestController
public class ReviewController {

    @ApiOperation("Reviews服务GET测试接口")
    @GetMapping(value = "/hello") // 任意请求
    public ResponseJsonBody hello1() {
        ResponseJsonBody responseJsonBody = new ResponseJsonBody();
        responseJsonBody.setCode(MallConstant.SUCCESS_CODE);
        responseJsonBody.setMsg("Users服务GET测试接口");
        return responseJsonBody;
    }

    @ApiOperation("Reviews服务POST测试接口")
    @PostMapping(value = "/hello") // 任意请求
    public ResponseJsonBody hello2() {
        ResponseJsonBody responseJsonBody = new ResponseJsonBody();
        responseJsonBody.setCode(MallConstant.SUCCESS_CODE);
        responseJsonBody.setMsg("Users服务POST测试接口");
        return responseJsonBody;
    }
}

