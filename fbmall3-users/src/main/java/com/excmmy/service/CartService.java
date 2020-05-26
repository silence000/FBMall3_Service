package com.excmmy.service;

import com.excmmy.bean.Cart;
import com.baomidou.mybatisplus.extension.service.IService;
import pojo.ResponseJsonBody;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Excmmy
 * @since 2020-05-26
 */
public interface CartService extends IService<Cart> {
    ResponseJsonBody insertProductInCart(Integer pid, Integer num);
}
