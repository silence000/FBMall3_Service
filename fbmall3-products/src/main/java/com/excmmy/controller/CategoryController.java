package com.excmmy.controller;


import com.excmmy.service.CategoryService;
import io.swagger.annotations.ApiOperation;
import org.bouncycastle.asn1.ocsp.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import pojo.ResponseJsonBody;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Excmmy
 * @since 2020-05-20
 */
@RestController
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @ApiOperation("获取分类列表接口")
    @GetMapping("/get/category")
    public ResponseJsonBody getCategory(
            @RequestParam(name = "current", required = false, defaultValue = "1") Integer current,
            @RequestParam(name = "size", required = false, defaultValue = "5") Integer size) {
        return categoryService.getCategory(current, size);
    }

    @ApiOperation("获取主页菜单接口")
    @GetMapping("/get/main_menu")
    public ResponseJsonBody getMainMenu(
            @RequestParam(name = "categoryId") Integer categoryId) {
        return categoryService.getMainMenu(categoryId);
    }
}

