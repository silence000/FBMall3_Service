package com.excmmy.controller;


import com.excmmy.model.RecInfoDTO;
import com.excmmy.service.OrdersService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pojo.ResponseJsonBody;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Excmmy
 * @since 2020-05-29
 */
@RestController
public class OrdersController {
    @Autowired
    private OrdersService ordersService;

    @ApiOperation("插入新订单")
    @PostMapping(value = "/p3/insert/order")
    public ResponseJsonBody insertOrders(@RequestBody RecInfoDTO recInfoDTO) {
        return ordersService.insertOrders(recInfoDTO);
    }

    @ApiOperation("支付订单")
    @PostMapping(value = "/p3/update/pay")
    public ResponseJsonBody payOrders(
            @ApiParam("订单ID") @RequestParam(name = "oid") Integer oid) {
        return ordersService.payOrders(oid);
    }
}

