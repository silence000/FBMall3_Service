package com.excmmy.service;

import com.excmmy.bean.Orders;
import com.baomidou.mybatisplus.extension.service.IService;
import com.excmmy.model.RecInfoDTO;
import pojo.ResponseJsonBody;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Excmmy
 * @since 2020-05-29
 */
public interface OrdersService extends IService<Orders> {
    ResponseJsonBody insertOrders(RecInfoDTO recInfo);

    ResponseJsonBody payOrders(Integer oid);
}
