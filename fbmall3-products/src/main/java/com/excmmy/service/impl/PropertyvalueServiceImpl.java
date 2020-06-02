package com.excmmy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.excmmy.bean.Product;
import com.excmmy.bean.Property;
import com.excmmy.bean.Propertyvalue;
import com.excmmy.mapper.ProductMapper;
import com.excmmy.mapper.PropertyMapper;
import com.excmmy.mapper.PropertyvalueMapper;
import com.excmmy.model.PropertyDTO;
import com.excmmy.service.PropertyvalueService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import pojo.MallConstant;
import pojo.ResponseJsonBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Excmmy
 * @since 2020-05-20
 */
@Service
public class PropertyvalueServiceImpl extends ServiceImpl<PropertyvalueMapper, Propertyvalue> implements PropertyvalueService {
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private PropertyMapper propertyMapper;
    @Autowired
    private PropertyvalueMapper propertyvalueMapper;

    @Override
    @Cacheable(value = "getProductExtra", key = "'productId=' + #id")
    public ResponseJsonBody getProductExtra(Integer id) {
        ResponseJsonBody responseJsonBody = new ResponseJsonBody();
        // 设置查询条件, 根据productId, 查询category
        QueryWrapper<Product> productQueryWrapper = new QueryWrapper<>();
        productQueryWrapper.eq("id", id);
        Product product = productMapper.selectOne(productQueryWrapper);
        if (product == null) {
            responseJsonBody.setCode(MallConstant.SUCCESS_CODE);
            responseJsonBody.setMsg(MallConstant.SUCCESS_DESC);
            return responseJsonBody;
        }
        // 根据所属分类, 查询Property
        Map<String,Object> PropertyColumnMap = new HashMap<>();
        PropertyColumnMap.put("cid", product.getCid());
        List<Property> propertyList = propertyMapper.selectByMap(PropertyColumnMap);
        // 创建传输对象
        List<PropertyDTO> propertyDTOList = new ArrayList<>();
        // 根据PropertyId与ProductId查询PropertyValue
        for (Property property : propertyList) {
            QueryWrapper<Propertyvalue> propertyvalueQueryWrapper = new QueryWrapper<>();
            propertyvalueQueryWrapper.eq("ptid", property.getId());
            propertyvalueQueryWrapper.eq("pid", id);
            Propertyvalue propertyvalue = propertyvalueMapper.selectOne(propertyvalueQueryWrapper);
            if (propertyvalue != null) {
                propertyDTOList.add(new PropertyDTO(property.getName(), propertyvalue.getValue()));
            }
        }
        responseJsonBody.setCode(MallConstant.SUCCESS_CODE);
        responseJsonBody.setMsg(MallConstant.SUCCESS_DESC);;
        responseJsonBody.setData(propertyDTOList);
        return responseJsonBody;
    }
}
