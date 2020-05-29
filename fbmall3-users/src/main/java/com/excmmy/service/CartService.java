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

    ResponseJsonBody getProductInCart(Integer current, Integer size);

    ResponseJsonBody updateProductInCart(Integer pid, Integer num);

    ResponseJsonBody deleteProductInCart(Integer pid);

    ResponseJsonBody getProductNum(Integer uid, Integer pid);

    // 此处没有用户权限验证，不应使用Feign调用
    ResponseJsonBody deleteProductInCartFeign(Integer uid, Integer pid);
}
