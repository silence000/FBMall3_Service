package com.excmmy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.excmmy.bean.Productimage;
import com.excmmy.mapper.ProductimageMapper;
import com.excmmy.model.ProductimageDTO;
import com.excmmy.service.ProductimageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import pojo.MallConstant;
import pojo.ResponseJsonBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Excmmy
 * @since 2020-05-20
 */
@Service
public class ProductimageServiceImpl extends ServiceImpl<ProductimageMapper, Productimage> implements ProductimageService {
    @Autowired
    private ProductimageMapper productimageMapper;

    @Override
    @Cacheable(value = "getProductDetailImages", key="'productId=' + #id + ';type=' + #type")
    public ResponseJsonBody getProductImages(Integer id, String type) {
        ResponseJsonBody responseJsonBody = new ResponseJsonBody();
        // 设置分页与查询条件
        Page<Productimage> productimagePage = new Page<>(1, 5);
        QueryWrapper<Productimage> productimageQueryWrapper = new QueryWrapper<>();
        productimageQueryWrapper.eq("pid", id);
        if (Objects.equals(type, "single")) {
            productimageQueryWrapper.eq("type", "single");
        } else {
            productimageQueryWrapper.eq("type", "detail");
        }
        // 执行查询
        productimageMapper.selectPage(productimagePage, productimageQueryWrapper);
        // 获取查询结果
        List<Productimage> productimageList = productimagePage.getRecords();
        if (productimageList.size() == 0) {
            responseJsonBody.setCode(MallConstant.SUCCESS_CODE);
            responseJsonBody.setMsg(MallConstant.SUCCESS_DESC);
            return responseJsonBody;
        }
        // 模型转换
        List<ProductimageDTO> productimageDTOList = new ArrayList<>();
        for (Productimage productimage : productimageList) {
            productimageDTOList.add(new ProductimageDTO(productimage.getId(), productimage.getId()));
        }
        // 返回数据
        responseJsonBody.setCode(MallConstant.SUCCESS_CODE);
        responseJsonBody.setMsg(MallConstant.SUCCESS_DESC);
        responseJsonBody.setData(productimageDTOList);
        return responseJsonBody;
    }

    @Override
    @Cacheable(value = "getOneProductImages", key = "'productId=' + #id")
    public ResponseJsonBody getOneProductImages(Integer id) {
        ResponseJsonBody responseJsonBody = new ResponseJsonBody();
        // 设置分页与查询条件
        Page<Productimage> productimagePage = new Page<>(1, 1);
        QueryWrapper<Productimage> productimageQueryWrapper = new QueryWrapper<>();
        productimageQueryWrapper.eq("pid", id);
        productimageQueryWrapper.eq("type", "single");
        // 执行查询
        productimageMapper.selectPage(productimagePage, productimageQueryWrapper);
        // 获取查询结果
        List<Productimage> productimageList = productimagePage.getRecords();
        if (productimageList.size() == 0) {
            responseJsonBody.setCode(MallConstant.SUCCESS_CODE);
            responseJsonBody.setMsg(MallConstant.SUCCESS_DESC);
            return responseJsonBody;
        }
        // 模型转换
        List<ProductimageDTO> productimageDTOList = new ArrayList<>();
        for (Productimage productimage : productimageList) {
            productimageDTOList.add(new ProductimageDTO(productimage.getId(), productimage.getId()));
        }
        // 返回数据
        responseJsonBody.setCode(MallConstant.SUCCESS_CODE);
        responseJsonBody.setMsg(MallConstant.SUCCESS_DESC);
        responseJsonBody.setData(productimageDTOList.get(0));
        return responseJsonBody;
    }
}
