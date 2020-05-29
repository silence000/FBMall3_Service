package com.excmmy.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.excmmy.bean.Orderitem;
import com.excmmy.bean.Orders;
import com.excmmy.feign.UsersServerFeign;
import com.excmmy.mapper.OrderitemMapper;
import com.excmmy.mapper.OrdersMapper;
import com.excmmy.model.RecInfoDTO;
import com.excmmy.service.OrdersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pojo.MallConstant;
import pojo.ResponseJsonBody;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Excmmy
 * @since 2020-05-29
 */
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {

    @Autowired
    private OrderitemMapper orderitemMapper;
    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private UsersServerFeign usersServerFeign;

    @Override
    @Transactional
    public ResponseJsonBody insertOrders(RecInfoDTO recInfo) {
        ResponseJsonBody responseJsonBody = new ResponseJsonBody();
        // 获取用户身份信息
        UserDTO userDTO = JSON.parseObject(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString(), UserDTO.class);
        // 填写 Order 相关信息
        Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyyMMddhhmmss");
        Orders order = new Orders();
        order.setOrderCode(dateFormat.format(date) + userDTO.getId());
        order.setAddress(recInfo.getRecAddress());
        order.setPost(recInfo.getPostcode());
        order.setReceiver(recInfo.getRecName());
        order.setMobile(recInfo.getRecPhone());
        order.setUserMessage(recInfo.getRemark());
        order.setCreateDate(date);
        order.setUid(new Integer(userDTO.getId()));
        order.setStatus("1");
        // 插入到 order 表
        int flag1 = ordersMapper.insert(order);
        if (flag1 != 1) {
            responseJsonBody.setCode(MallConstant.FAIL_CODE);
            responseJsonBody.setMsg(MallConstant.FAIL_DESC);
            return responseJsonBody;
        }
        // 根据商品ID查询对应购物车中商品的数量
        String[] productsId = recInfo.getProductsId().split(" ");
        for (String productId : productsId) {
            ResponseJsonBody usersResponse1 = usersServerFeign.getProductNum(new Integer(userDTO.getId()), new Integer(productId));
            if (usersResponse1.getData() == null) {
                responseJsonBody.setCode(MallConstant.FAIL_CODE);
                responseJsonBody.setMsg(MallConstant.FAIL_DESC);
                return responseJsonBody;
            }
            Orderitem orderitem = new Orderitem();
            orderitem.setPid(new Integer(productId));
            orderitem.setOid(order.getId());
            orderitem.setUid(new Integer(userDTO.getId()));
            orderitem.setNumber(new Integer(usersResponse1.getData() + ""));
            // 插入 orderItem
            int flag2 = orderitemMapper.insert(orderitem);
            if (flag2 != 1) {
                responseJsonBody.setCode(MallConstant.FAIL_CODE);
                responseJsonBody.setMsg(MallConstant.FAIL_DESC);
                return responseJsonBody;
            }
            // 删除购物车中对应的条目
            ResponseJsonBody usersResponse2 = usersServerFeign.deleteProductInCartFeign(new Integer(userDTO.getId()), new Integer(productId));
            if (usersResponse2.getCode() != 1) {
                responseJsonBody.setCode(MallConstant.FAIL_CODE);
                responseJsonBody.setMsg(MallConstant.FAIL_DESC);
                return responseJsonBody;
            }
        }
        responseJsonBody.setCode(MallConstant.SUCCESS_CODE);
        responseJsonBody.setMsg(MallConstant.SUCCESS_DESC);
        responseJsonBody.setData(order.getId());
        return responseJsonBody;
    }

    @Override
    public ResponseJsonBody payOrders(Integer oid) {
        ResponseJsonBody responseJsonBody = new ResponseJsonBody();
        // 获取用户身份信息
        UserDTO userDTO = JSON.parseObject(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString(), UserDTO.class);
        QueryWrapper<Orders> ordersQueryWrapper = new QueryWrapper<>();
        ordersQueryWrapper.eq("id", oid);
        ordersQueryWrapper.eq("uid", userDTO.getId());
        Orders ordersUpdate = new Orders();
        ordersUpdate.setId(oid);
        ordersUpdate.setStatus("2");
        ordersUpdate.setPayDate(new Date());
        int flag = ordersMapper.update(ordersUpdate, ordersQueryWrapper);
        if (flag != 1) {
            responseJsonBody.setCode(MallConstant.FAIL_CODE);
            responseJsonBody.setMsg(MallConstant.FAIL_DESC);
            return responseJsonBody;
        }
        responseJsonBody.setCode(MallConstant.SUCCESS_CODE);
        responseJsonBody.setMsg(MallConstant.SUCCESS_DESC);
        return responseJsonBody;
    }
}
