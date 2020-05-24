package com.excmmy.controller;


import com.excmmy.service.ProductService;
import io.swagger.annotations.ApiOperation;
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
 * @since 2020-05-20
 */
@RestController
public class ProductController {
    @Autowired
    private ProductService productService;

    @ApiOperation("Products服务GET测试接口")
    @GetMapping(value = "/hello") // 任意请求
    public ResponseJsonBody hello1() {
        ResponseJsonBody responseJsonBody = new ResponseJsonBody();
        responseJsonBody.setCode(MallConstant.SUCCESS_CODE);
        responseJsonBody.setMsg("Products服务GET测试接口");
        return responseJsonBody;
    }

    @ApiOperation("Products服务POST测试接口")
    @PostMapping(value = "/hello") // 任意请求
    public ResponseJsonBody hello2() {
        ResponseJsonBody responseJsonBody = new ResponseJsonBody();
        responseJsonBody.setCode(MallConstant.SUCCESS_CODE);
        responseJsonBody.setMsg("Products服务POST测试接口");
        return responseJsonBody;
    }

    @ApiOperation("获取主页推荐列表接口")
    @GetMapping(value = "/get/main_list")
    public ResponseJsonBody getListProductInfo(
            @RequestParam(name = "cateCurrent")Integer cateCurrent,
            @RequestParam(name = "cateSize")Integer cateSize,
            @RequestParam(name = "productCurrent")Integer productCurrent,
            @RequestParam(name = "productSize")Integer productSize) {
        return productService.getListProductInfo(cateCurrent, cateSize, productCurrent, productSize);
    }

    @ApiOperation("获取商品详情页商品详细信息接口")
    @GetMapping(value = "/get/product_details")
    public ResponseJsonBody getProductDetails(@RequestParam(name = "id") Integer id) {
        return productService.getProductDetails(id);
    }
}

