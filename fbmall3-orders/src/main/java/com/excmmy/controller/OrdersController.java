package com.excmmy.controller;


import com.excmmy.model.RecInfoDTO;
import com.excmmy.service.OrdersService;
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

    @ApiOperation("获取订单列表")
    @GetMapping(value = "/p3/get/orders")
    public ResponseJsonBody getOrders(
            @ApiParam("当前页") @RequestParam(name = "current") Integer current,
            @ApiParam("页面大小") @RequestParam(name = "size") Integer size,
            @ApiParam("订单状态") @RequestParam(name = "status", defaultValue = "0") String type) {
        return ordersService.getOrders(current, size, type);
    }

    @ApiOperation("获取一个订单的详情")
    @GetMapping(value = "/p3/get/order/details")
    public ResponseJsonBody getOneOrder(
            @ApiParam("OrderID") @RequestParam(name = "oid") Integer oid) {
        return ordersService.getOneOrder(oid);
    }

    @ApiOperation("支付订单")
    @PostMapping(value = "/p3/update/pay")
    public ResponseJsonBody payOrders(
            @ApiParam("订单ID") @RequestParam(name = "oid") Integer oid) {
        return ordersService.updateOrderState(oid, 2);
    }

    @ApiOperation("卖家发货")
    @PostMapping(value = "/p3/update/distribute")
    public ResponseJsonBody distributeOrders(
            @ApiParam("订单ID") @RequestParam(name = "oid") Integer oid) {
        return ordersService.updateOrderState(oid, 3);
    }

    @ApiOperation("确认收货")
    @PostMapping(value = "/p3/update/confirm")
    public ResponseJsonBody confirmOrders(
            @ApiParam("订单ID") @RequestParam(name = "oid") Integer oid) {
        return ordersService.updateOrderState(oid, 4);
    }
}

