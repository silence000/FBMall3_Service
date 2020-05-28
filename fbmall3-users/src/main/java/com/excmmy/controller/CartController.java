package com.excmmy.controller;


import com.excmmy.service.CartService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import pojo.ResponseJsonBody;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Excmmy
 * @since 2020-05-26
 */
@RestController
public class CartController {
    @Autowired
    private CartService cartService;

    @ApiOperation("商品加入到购物车")
    @PostMapping(value = "/p3/insert/cart")
    public ResponseJsonBody insertProductInCart(
            @ApiParam("商品ID") @RequestParam(name = "pid")Integer pid,
            @ApiParam("商品数量") @RequestParam(name = "num") Integer num){
        return cartService.insertProductInCart(pid, num);
    }

    @ApiOperation("获取当前用户购物车信息")
    @GetMapping(value = "/p3/get/cart")
    public ResponseJsonBody getProductInCart(
            @ApiParam("当前页") @RequestParam(name = "current", defaultValue = "1") Integer current,
            @ApiParam("页面大小") @RequestParam(name = "size", defaultValue = "10") Integer size){
        return cartService.getProductInCart(current, size);
    }

    @ApiOperation("更新购物车中商品的数量")
    @PostMapping(value = "/p3/update/cart")
    public ResponseJsonBody updateProductInCart(
            @ApiParam("商品ID") @RequestParam(name = "pid") Integer pid,
            @ApiParam("更新后的商品数量") @RequestParam(name = "num") Integer num) {
        return cartService.updateProductInCart(pid, num);
    }

    @ApiOperation("删除购物车中的商品")
    @PostMapping(value = "/p3/delete/cart")
    public ResponseJsonBody deleteProductInCart(
            @ApiParam("商品ID") @RequestParam(name = "pid") Integer pid) {
        return cartService.deleteProductInCart(pid);
    }
}

