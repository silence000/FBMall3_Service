package com.excmmy.controller;


import com.excmmy.service.ReviewService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    private ReviewService reviewService;

    @ApiOperation("Reviews服务GET测试接口")
    @GetMapping(value = "/hello") // 任意请求
    public ResponseJsonBody hello1() {
        ResponseJsonBody responseJsonBody = new ResponseJsonBody();
        responseJsonBody.setCode(MallConstant.SUCCESS_CODE);
        responseJsonBody.setMsg("Users服务GET测试接口");
        return responseJsonBody;
    }

    @ApiOperation("Reviews服务POST测试")
    @PostMapping(value = "/hello") // 任意请求
    public ResponseJsonBody hello2() {
        ResponseJsonBody responseJsonBody = new ResponseJsonBody();
        responseJsonBody.setCode(MallConstant.SUCCESS_CODE);
        responseJsonBody.setMsg("Users服务POST测试接口");
        return responseJsonBody;
    }

    @ApiOperation("获取商品评论总数量")
    @GetMapping(value = "/get/reviews_number")
    public ResponseJsonBody getReviewsNumber(
            @ApiParam("商品ID") @RequestParam(value = "id") Integer id) {
        return reviewService.getReviewsNumber(id);
    }

    @ApiOperation("获取商品评论")
    @GetMapping(value = "/get/reviews")
    public ResponseJsonBody getReviews(
            @ApiParam("商品ID") @RequestParam(value = "id") Integer id) {
        return reviewService.getReviews(id, 1L, 5L);
    }
}

