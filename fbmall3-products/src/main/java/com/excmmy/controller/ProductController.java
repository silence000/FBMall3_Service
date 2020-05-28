package com.excmmy.controller;


import com.excmmy.service.ProductService;
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
 * @since 2020-05-20
 */
@RestController
public class ProductController {
    @Autowired
    private ProductService productService;

    @ApiOperation("Products服务GET测试")
    @GetMapping(value = "/hello") // 任意请求
    public ResponseJsonBody hello1() {
        ResponseJsonBody responseJsonBody = new ResponseJsonBody();
        responseJsonBody.setCode(MallConstant.SUCCESS_CODE);
        responseJsonBody.setMsg("Products服务GET测试接口");
        return responseJsonBody;
    }

    @ApiOperation("Products服务POST测试")
    @PostMapping(value = "/hello") // 任意请求
    public ResponseJsonBody hello2() {
        ResponseJsonBody responseJsonBody = new ResponseJsonBody();
        responseJsonBody.setCode(MallConstant.SUCCESS_CODE);
        responseJsonBody.setMsg("Products服务POST测试接口");
        return responseJsonBody;
    }

    @ApiOperation("获取主页推荐列表")
    @GetMapping(value = "/get/main_list")
    public ResponseJsonBody getListProductInfo(
            @ApiParam("分类当前页") @RequestParam(name = "cateCurrent")Integer cateCurrent,
            @ApiParam("分类页面大小") @RequestParam(name = "cateSize")Integer cateSize,
            @ApiParam("商品当前页") @RequestParam(name = "productCurrent")Integer productCurrent,
            @ApiParam("商品页面大小") @RequestParam(name = "productSize")Integer productSize) {
        return productService.getListProductInfo(cateCurrent, cateSize, productCurrent, productSize);
    }

    @ApiOperation("获取商品详情页商品详细信息")
    @GetMapping(value = "/get/product_details")
    public ResponseJsonBody getProductDetails(
            @ApiParam("商品ID") @RequestParam(name = "id") Integer id) {
        return productService.getProductDetails(id);
    }

    @ApiOperation("根据条件查询商品")
    @GetMapping(value = "/get/product/condition")
    public ResponseJsonBody getProductByConditions(
            @ApiParam("商品名称") @RequestParam(name = "productName", defaultValue = "nul") String name,
            @ApiParam("商品所属分类ID") @RequestParam(name = "cid", defaultValue = "0") Integer cid,
            @ApiParam("当前页") @RequestParam(name = "current") Integer current,
            @ApiParam("页面大小") @RequestParam(name = "size") Integer size,
            @ApiParam("分类排序类型") @RequestParam(name = "sortType", defaultValue = "none") String sortType,
            @ApiParam("最低价格") @RequestParam(name = "lowPrice", defaultValue = "0") Integer low,
            @ApiParam("最高价格") @RequestParam(name = "heiPrice", defaultValue = "99999999") Integer high) {
        return productService.getProductByConditions(name, cid, current, size, sortType, low, high);
    }
}

