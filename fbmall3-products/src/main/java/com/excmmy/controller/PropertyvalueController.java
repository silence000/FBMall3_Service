package com.excmmy.controller;


import com.excmmy.service.PropertyvalueService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
public class PropertyvalueController {
    @Autowired
    private PropertyvalueService propertyvalueService;

    @ApiOperation("获取商品详情页商品属性接口")
    @GetMapping(value = "/get/product_extra")
    public ResponseJsonBody getProductExtra(@RequestParam(name = "id")Integer id) {
        return propertyvalueService.getProductExtra(id);
    }
}

