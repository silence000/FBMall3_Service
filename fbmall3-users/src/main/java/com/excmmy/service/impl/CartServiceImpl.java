package com.excmmy.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.excmmy.bean.Cart;
import com.excmmy.feign.ProductsServerFeign;
import com.excmmy.mapper.CartMapper;
import com.excmmy.model.CartProductDTO;
import model.ProductimageDTO;
import model.ProductsDetailsDTO;
import com.excmmy.service.CartService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pojo.MallConstant;
import pojo.ResponseJsonBody;
import model.UserDTO;

import java.util.ArrayList;
import java.util.List;

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
    @Autowired
    private ProductsServerFeign productsServerFeign;

    @Override
    public ResponseJsonBody insertProductInCart(Integer pid, Integer num) {
        ResponseJsonBody responseJsonBody = new ResponseJsonBody();
        // 获取用户身份信息
        UserDTO userDTO = JSON.parseObject(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString(), UserDTO.class);
        // 查询该商品是否已存在用户的购物车中
        QueryWrapper<Cart> cartQueryWrapper = new QueryWrapper<>();
        cartQueryWrapper.eq("uid", userDTO.getId());
        cartQueryWrapper.eq("pid", pid);
        Cart cartResult = cartMapper.selectOne(cartQueryWrapper);
        int flag = 0;
        if (cartResult == null) { // 若该商品未存在购物车中, 则插入数据
            Cart cartInsert = new Cart();
            cartInsert.setUid(new Integer(userDTO.getId()));
            cartInsert.setPid(pid);
            cartInsert.setNumber(num);
            flag = cartMapper.insert(cartInsert);
        } else {
            Cart cartUpdate = new Cart();
            cartUpdate.setId(cartResult.getId());
            cartUpdate.setNumber(num + cartResult.getNumber());
            flag = cartMapper.updateById(cartUpdate);
        }
        if (flag == 1) {
            responseJsonBody.setCode(MallConstant.SUCCESS_CODE);
            responseJsonBody.setMsg(MallConstant.SUCCESS_DESC);
        } else {
            responseJsonBody.setCode(MallConstant.FAIL_CODE);
            responseJsonBody.setMsg(MallConstant.FAIL_DESC);
        }
        return responseJsonBody;
    }

    @Override
    public ResponseJsonBody getProductInCart(Integer current, Integer size) {
        ResponseJsonBody responseJsonBody = new ResponseJsonBody();
        // 获取用户身份信息
        UserDTO userDTO = JSON.parseObject(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString(), UserDTO.class);
        // 设置查询条件与分页
        QueryWrapper<Cart> cartQueryWrapper = new QueryWrapper<>();
        Page<Cart> cartPage = new Page<>(current, size);
        cartQueryWrapper.eq("uid", userDTO.getId());
        // 执行查询
        cartMapper.selectPage(cartPage, cartQueryWrapper);
        // 获取查询结果
        List<Cart> cartList = cartPage.getRecords();
        if (cartList == null) {
            responseJsonBody.setCode(MallConstant.SUCCESS_CODE);
            responseJsonBody.setMsg(MallConstant.SUCCESS_DESC);
            return responseJsonBody;
        }
        // 模型转换
        List<CartProductDTO> cartProductDTOList = new ArrayList<>();
        for (Cart cart : cartList) {
            CartProductDTO cartProductDTO = new CartProductDTO();
            cartProductDTO.setId(cart.getPid()); // 放入商品ID
            cartProductDTO.setNumber(cart.getNumber()); // 放入该商品的数量
            // Feign调用填写商品信息
            ResponseJsonBody productResponse = productsServerFeign.getProductDetails(cart.getPid());
            ObjectMapper objectMapper = new ObjectMapper();
            ProductsDetailsDTO productsDetailsDTO = objectMapper.convertValue(productResponse.getData(), ProductsDetailsDTO.class);
            cartProductDTO.setName(productsDetailsDTO.getName());
            cartProductDTO.setPromotePrice(productsDetailsDTO.getPromotePrice());
            cartProductDTO.setOriginalPrice(productsDetailsDTO.getOriginalPrice());
            // Feign调用填写商品的一张图片
            ResponseJsonBody productImageResponse = productsServerFeign.getOneProductImages(cart.getPid());
            ObjectMapper objectMapper2 = new ObjectMapper();
            ProductimageDTO productimageDTO = objectMapper2.convertValue(productImageResponse.getData(), ProductimageDTO.class);
            cartProductDTO.setImgUrl(productimageDTO.getLink());
            // 提交数据
            cartProductDTOList.add(cartProductDTO);
            responseJsonBody.setCode(MallConstant.SUCCESS_CODE);
            responseJsonBody.setMsg(MallConstant.SUCCESS_DESC);
            responseJsonBody.setData(cartProductDTOList);
        }
        return responseJsonBody;
    }

    @Override
    public ResponseJsonBody updateProductInCart(Integer pid, Integer num) {
        ResponseJsonBody responseJsonBody = new ResponseJsonBody();
        // 获取用户身份信息
        UserDTO userDTO = JSON.parseObject(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString(), UserDTO.class);
        // 查询原信息
        QueryWrapper<Cart> cartQueryWrapper = new QueryWrapper<>();
        cartQueryWrapper.eq("uid", userDTO.getId());
        cartQueryWrapper.eq("pid", pid);
        // 根据CartId更新内容
        Cart cartUpdate = new Cart();
        cartUpdate.setNumber(num);
        int flag = cartMapper.update(cartUpdate, cartQueryWrapper);
        if (flag != 1) {
            responseJsonBody.setCode(MallConstant.FAIL_CODE);
            responseJsonBody.setMsg(MallConstant.FAIL_DESC);
            return responseJsonBody;
        }
        responseJsonBody.setCode(MallConstant.SUCCESS_CODE);
        responseJsonBody.setMsg(MallConstant.SUCCESS_DESC);
        return responseJsonBody;
    }

    @Override
    public ResponseJsonBody deleteProductInCart(Integer pid) {
        ResponseJsonBody responseJsonBody = new ResponseJsonBody();
        // 获取用户身份信息
        UserDTO userDTO = JSON.parseObject(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString(), UserDTO.class);
        QueryWrapper<Cart> cartQueryWrapper = new QueryWrapper<>();
        cartQueryWrapper.eq("uid", userDTO.getId());
        cartQueryWrapper.eq("pid", pid);
        int flag = cartMapper.delete(cartQueryWrapper);
        if (flag != 1) {
            responseJsonBody.setCode(MallConstant.FAIL_CODE);
            responseJsonBody.setMsg(MallConstant.FAIL_DESC);
            return responseJsonBody;
        }
        responseJsonBody.setCode(MallConstant.SUCCESS_CODE);
        responseJsonBody.setMsg(MallConstant.SUCCESS_DESC);
        return responseJsonBody;
    }

    @Override
    public ResponseJsonBody getProductNum(Integer uid, Integer pid) {
        ResponseJsonBody responseJsonBody = new ResponseJsonBody();
        // 设置查询条件
        QueryWrapper<Cart> cartQueryWrapper = new QueryWrapper<>();
        cartQueryWrapper.eq("uid", uid);
        cartQueryWrapper.eq("pid", pid);
        // 执行查询
        Cart cart = cartMapper.selectOne(cartQueryWrapper);
        if (cart == null) {
            responseJsonBody.setCode(MallConstant.SUCCESS_CODE);
            responseJsonBody.setMsg(MallConstant.SUCCESS_DESC);
            return responseJsonBody;
        }
        responseJsonBody.setCode(MallConstant.SUCCESS_CODE);
        responseJsonBody.setMsg(MallConstant.SUCCESS_DESC);
        responseJsonBody.setData(cart.getNumber());
        return responseJsonBody;
    }

    @Override
    public ResponseJsonBody deleteProductInCartFeign(Integer uid, Integer pid) {
        ResponseJsonBody responseJsonBody = new ResponseJsonBody();
        QueryWrapper<Cart> cartQueryWrapper = new QueryWrapper<>();
        cartQueryWrapper.eq("uid", uid);
        cartQueryWrapper.eq("pid", pid);
        int flag = cartMapper.delete(cartQueryWrapper);
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
