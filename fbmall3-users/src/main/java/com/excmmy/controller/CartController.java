package com.excmmy.controller;


import com.excmmy.service.CartService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
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
 * @since 2020-05-26
 */
@RestController
public class CartController {
    @Autowired
    private CartService cartService;


    @ApiOperation("商品加入到购物车接口")
    @PostMapping(value = "/p3/insert/cart")
    public ResponseJsonBody insertProductInCart(
            @RequestParam(name = "pid")Integer pid,
            @RequestParam(name = "num") Integer num){
        return cartService.insertProductInCart(pid, num);
    }
}

