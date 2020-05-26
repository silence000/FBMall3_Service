package com.excmmy.service.impl;

import com.alibaba.fastjson.JSON;
import com.excmmy.bean.Cart;
import com.excmmy.mapper.CartMapper;
import com.excmmy.service.CartService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pojo.MallConstant;
import pojo.ResponseJsonBody;
import pojo.UserDTO;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Excmmy
 * @since 2020-05-26
 */
@Service
public class CartServiceImpl extends ServiceImpl<CartMapper, Cart> implements CartService {
    @Autowired
    private CartMapper cartMapper;

    @Override
    public ResponseJsonBody insertProductInCart(Integer pid, Integer num) {
        ResponseJsonBody responseJsonBody = new ResponseJsonBody();
        // 获取用户身份信息
        UserDTO userDTO = JSON.parseObject(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString(), UserDTO.class);
        Cart cartItem = new Cart();
        cartItem.setUid(new Integer(userDTO.getId()));
        cartItem.setPid(pid);
        cartItem.setNumber(num);
        int flag = cartMapper.insert(cartItem);
        if (flag == 1) {
            responseJsonBody.setCode(MallConstant.SUCCESS_CODE);
            responseJsonBody.setMsg(MallConstant.SUCCESS_DESC);
        } else {
            responseJsonBody.setCode(MallConstant.FAIL_CODE);
            responseJsonBody.setMsg(MallConstant.FAIL_DESC);
        }
        return responseJsonBody;
    }
}
