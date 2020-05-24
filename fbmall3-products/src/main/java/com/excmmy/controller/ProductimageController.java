package com.excmmy.controller;


import com.excmmy.service.ProductimageService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
public class ProductimageController {
    @Autowired
    private ProductimageService productimageService;

    @ApiOperation("获取商品详情页商品图片名称接口")
    @GetMapping(value = "/get/product_detail_image")
    public ResponseJsonBody getProductDetailImages(@RequestParam(name = "id")Integer id){
        return productimageService.getProductImages(id, "single");
    }

    @ApiOperation("获取商品详情页商品Extra图片名称接口")
    @GetMapping(value = "/get/product_extra_image")
    public ResponseJsonBody getImageExtraUrl(@RequestParam(name = "id")Integer id){
        return productimageService.getProductImages(id, "detail");
    }
}

