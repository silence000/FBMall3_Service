package com.excmmy.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.excmmy.bean.Dictionary;
import com.excmmy.bean.Orderitem;
import com.excmmy.bean.Orders;
import com.excmmy.feign.ProductsServerFeign;
import com.excmmy.feign.UsersServerFeign;
import com.excmmy.mapper.DictionaryMapper;
import com.excmmy.mapper.OrderitemMapper;
import com.excmmy.mapper.OrdersMapper;
import com.excmmy.model.OrderDetailsDTO;
import com.excmmy.model.OrdersDTO;
import com.excmmy.model.OrderItemDTO;
import com.excmmy.model.RecInfoDTO;
import com.excmmy.service.OrdersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import model.ProductimageDTO;
import model.ProductsDetailsDTO;
import model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pojo.MallConstant;
import pojo.ResponseJsonBody;

import java.text.SimpleDateFormat;
import java.util.*;

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
    private DictionaryMapper dictionaryMapper;
    @Autowired
    private UsersServerFeign usersServerFeign;
    @Autowired
    private ProductsServerFeign productsServerFeign;

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
    public ResponseJsonBody getOrders(Integer current, Integer size, String type) {
        ResponseJsonBody responseJsonBody = new ResponseJsonBody();
        // 获取用户身份信息
        UserDTO userDTO = JSON.parseObject(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString(), UserDTO.class);
        // 设置 Orders 的分页与查询条件
        Page<Orders> ordersPage = new Page<>(current, size);
        QueryWrapper<Orders> ordersQueryWrapper = new QueryWrapper<>();
        ordersQueryWrapper.eq("uid", userDTO.getId());
        if (!Objects.equals(type, "0")) {
            ordersQueryWrapper.eq("status", type);
        }
        ordersQueryWrapper.orderByDesc("gmtCreate"); // 根据订单创建时间降序排列
        // 执行查询
        ordersMapper.selectPage(ordersPage, ordersQueryWrapper);
        // 获取查询结果
        List<Orders> ordersList = ordersPage.getRecords();
        // 创建最终返回数据模型
        List<Object> ordersListData = new ArrayList<>();
        for (Orders orders : ordersList) {
            // 创建子返回数据模型
            List<Object> ordersListItem = new ArrayList<>();
            // 根据uid与oid查询 ordersItem
            Map<String,Object> orderitemColumnMap = new HashMap<>();
            orderitemColumnMap.put("uid", userDTO.getId());
            orderitemColumnMap.put("oid", orders.getId());
            List<Orderitem> orderitemList = orderitemMapper.selectByMap(orderitemColumnMap);
            // 根据 Status 查询状态说明
            QueryWrapper<Dictionary> dictionaryQueryWrapper = new QueryWrapper<>();
            dictionaryQueryWrapper.eq("exType", 10);
            dictionaryQueryWrapper.eq("exKey", orders.getStatus());
            Dictionary dictionary = dictionaryMapper.selectOne(dictionaryQueryWrapper);
            // 填充 ordersListItem 第 1 段数据
            ordersListItem.add(new OrdersDTO(orders.getId(), orders.getCreateDate(), orders.getOrderCode(), orders.getStatus(), dictionary.getExValue(), ""));
            // 根据 pid 查询 对应的商品信息
            for (Orderitem orderitem : orderitemList) {
                ResponseJsonBody productResponse = productsServerFeign.getProductDetails(orderitem.getPid());
                if (productResponse.getData() == null) {
                    responseJsonBody.setCode(MallConstant.FAIL_CODE);
                    responseJsonBody.setMsg(MallConstant.FAIL_DESC);
                    return responseJsonBody;
                }
                // 将Json数据转换为实体类
                ProductsDetailsDTO productsDetailsDTO = JSON.parseObject(JSON.toJSONString(productResponse.getData()), ProductsDetailsDTO.class);
                // 查询图片信息
                ResponseJsonBody productImageResponse = productsServerFeign.getOneProductImages(orderitem.getPid());
                if (productImageResponse.getData() == null) {
                    responseJsonBody.setCode(MallConstant.FAIL_CODE);
                    responseJsonBody.setMsg(MallConstant.FAIL_DESC);
                    return responseJsonBody;
                }
                // 将Json数据转换为实体类
                ProductimageDTO productimageDTO = JSON.parseObject(JSON.toJSONString(productImageResponse.getData()), ProductimageDTO.class);
                // 填充 ordersListItem 第 2 段数据
                ordersListItem.add(new OrderItemDTO(productsDetailsDTO.getId(), productsDetailsDTO.getName(), productsDetailsDTO.getSubTitle(), productsDetailsDTO.getOriginalPrice(), productsDetailsDTO.getPromotePrice(), orderitem.getNumber(), productimageDTO.getLink() + ""));
            }
            ordersListData.add(ordersListItem);
        }
        responseJsonBody.setCode(MallConstant.SUCCESS_CODE);
        responseJsonBody.setMsg(MallConstant.SUCCESS_DESC);
        responseJsonBody.setData(ordersListData);
        return responseJsonBody;
    }

    @Override
    public ResponseJsonBody getOneOrder(Integer oid) {
        ResponseJsonBody responseJsonBody = new ResponseJsonBody();
        // 获取用户身份信息
        UserDTO userDTO = JSON.parseObject(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString(), UserDTO.class);
        // 创建最终数据返回模型
        List<OrderItemDTO> orderItemDTOList = new ArrayList<>();
        OrderDetailsDTO orderDetailsDTO = new OrderDetailsDTO();
        // 设置查询条件查询 orderitem 表
        HashMap<String, Object> orderItemCondition = new HashMap<>();
        orderItemCondition.put("uid", userDTO.getId());
        orderItemCondition.put("oid", oid);
        // 获取该订单的 商品ID 与 商品数量
        List<Orderitem> orderitemList = orderitemMapper.selectByMap(orderItemCondition);
        if (orderitemList.size() == 0) {
            responseJsonBody.setCode(MallConstant.FAIL_CODE);
            responseJsonBody.setMsg(MallConstant.FAIL_DESC);
            return responseJsonBody;
        }
        // 设置查询条件查询 order 表
        QueryWrapper<Orders> ordersQueryWrapper = new QueryWrapper<>();
        ordersQueryWrapper.eq("uid", userDTO.getId());
        ordersQueryWrapper.eq("id", oid);
        // 获取该订单的详情
        Orders orders = ordersMapper.selectOne(ordersQueryWrapper);
        if (orders == null) {
            responseJsonBody.setCode(MallConstant.FAIL_CODE);
            responseJsonBody.setMsg(MallConstant.FAIL_DESC);
            return responseJsonBody;
        }
        // 填充 OrderDetailsDTO orderDetailsDTO 数据
        orderDetailsDTO.setId(oid);
        orderDetailsDTO.setCreateDate(orders.getCreateDate());
        orderDetailsDTO.setPayDate(orders.getPayDate());
        orderDetailsDTO.setOrderCode(orders.getOrderCode());
        orderDetailsDTO.setAddress(orders.getAddress());
        orderDetailsDTO.setReceiver(orders.getReceiver());
        orderDetailsDTO.setMobile(orders.getMobile());
        orderDetailsDTO.setPost(orders.getPost());
        // 获取商品描述
        for (Orderitem orderitem : orderitemList) {
            ResponseJsonBody productResponse = productsServerFeign.getProductDetails(orderitem.getPid());
            if (productResponse.getData() == null) {
                responseJsonBody.setCode(MallConstant.FAIL_CODE);
                responseJsonBody.setMsg(MallConstant.FAIL_DESC);
                return responseJsonBody;
            }
            // 将Json数据转换为实体类
            ProductsDetailsDTO productsDetailsDTO = JSON.parseObject(JSON.toJSONString(productResponse.getData()), ProductsDetailsDTO.class);
            // 查询图片信息
            ResponseJsonBody productImageResponse = productsServerFeign.getOneProductImages(orderitem.getPid());
            if (productImageResponse.getData() == null) {
                responseJsonBody.setCode(MallConstant.FAIL_CODE);
                responseJsonBody.setMsg(MallConstant.FAIL_DESC);
                return responseJsonBody;
            }
            // 将Json数据转换为实体类
            ProductimageDTO productimageDTO = JSON.parseObject(JSON.toJSONString(productImageResponse.getData()), ProductimageDTO.class);
            // 填充 List<OrderItemDTO> orderItemDTOList 数据
            orderItemDTOList.add(new OrderItemDTO(orderitem.getPid(), productsDetailsDTO.getName(), productsDetailsDTO.getSubTitle(), productsDetailsDTO.getOriginalPrice(), productsDetailsDTO.getPromotePrice(), orderitem.getNumber(), productimageDTO.getLink() + ""));
        }
        responseJsonBody.setCode(MallConstant.SUCCESS_CODE);
        responseJsonBody.setMsg(MallConstant.SUCCESS_DESC);
        responseJsonBody.setData(orderItemDTOList);
        responseJsonBody.setExtra(orderDetailsDTO);
        return responseJsonBody;
    }

    @Override
    public ResponseJsonBody updateOrderState(Integer oid, Integer status) {
        ResponseJsonBody responseJsonBody = new ResponseJsonBody();
        // 获取用户身份信息
        UserDTO userDTO = JSON.parseObject(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString(), UserDTO.class);
        // 设置查询条件
        QueryWrapper<Orders> ordersQueryWrapper = new QueryWrapper<>();
        ordersQueryWrapper.eq("id", oid);
        ordersQueryWrapper.eq("uid", userDTO.getId());
        // 设置更新实体
        Orders orders = new Orders();
        orders.setId(oid);
        orders.setStatus(status + "");
        if (status == 2) { // 待付款 -> 待发货 (支付过程)
            orders.setPayDate(new Date());
        }
        if (status == 3) { // 待发货 -> 待收货 (卖家发货过程)
            orders.setDeliveryDate(new Date());
        }
        if (status == 4) { // 待收货 -> 待评价 (卖家确认收货过程)
            orders.setConfirmDate(new Date());
        }
        int flag = ordersMapper.update(orders, ordersQueryWrapper);
        if (flag == 0) {
            responseJsonBody.setCode(MallConstant.FAIL_CODE);
            responseJsonBody.setMsg(MallConstant.FAIL_DESC);
            return responseJsonBody;
        }
        responseJsonBody.setCode(MallConstant.SUCCESS_CODE);
        responseJsonBody.setMsg(MallConstant.SUCCESS_DESC);
        return responseJsonBody;
    }
}
