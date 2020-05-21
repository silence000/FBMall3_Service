package com.excmmy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.excmmy.bean.Category;
import com.excmmy.bean.Product;
import com.excmmy.mapper.CategoryMapper;
import com.excmmy.mapper.ProductMapper;
import com.excmmy.model.CategoryDTO;
import com.excmmy.model.SimpleProductDTO;
import com.excmmy.service.CategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import pojo.MallConstant;
import pojo.PageInfo;
import pojo.ResponseJsonBody;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Excmmy
 * @since 2020-05-20
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ProductMapper productMapper;

    @Override
    @Cacheable(value = "getCategory", key = "'current=' + #current+':'+'size='+#size")
    public ResponseJsonBody getCategory(Integer current, Integer size) {
        ResponseJsonBody responseJsonBody = new ResponseJsonBody();
        // 设置分页与查询条件
        Page<Category> categoryPage = new Page<>(current, size);
        QueryWrapper<Category> categoryQueryWrapper = new QueryWrapper<>();
        categoryQueryWrapper.eq("isDelete", "0");
        // 执行查询
        categoryMapper.selectPage(categoryPage, categoryQueryWrapper);
        // 获取查询结果
        List<Category> categoryList = categoryPage.getRecords();
        // 设置分页对象
        PageInfo pageInfoResult = new PageInfo();
        pageInfoResult.setCurrentPage(categoryPage.getCurrent());
        pageInfoResult.setPages(categoryPage.getPages());
        pageInfoResult.setPageSize(categoryPage.getSize());
        pageInfoResult.setTotal(categoryPage.getTotal());
        pageInfoResult.setHasNext(categoryPage.hasNext());
        pageInfoResult.setHasPrevious(categoryPage.hasPrevious());
        // 模型转换
        List<CategoryDTO> categoryDTOList = new ArrayList<>();
        for (Category category : categoryList) {
            categoryDTOList.add(new CategoryDTO(category.getId(), category.getName()));
        }
        // 返回数据
        responseJsonBody.setCode(MallConstant.SUCCESS_CODE);
        responseJsonBody.setMsg(MallConstant.SUCCESS_DESC);
        responseJsonBody.setData(categoryDTOList);
        responseJsonBody.setExtra(pageInfoResult);
        return responseJsonBody;
    }

    @Override
    @Cacheable(value = "getMainMenu", key = "'categoryId='+#categoryId")
    public ResponseJsonBody getMainMenu(Integer categoryId) {
        ResponseJsonBody responseJsonBody = new ResponseJsonBody();
        // 设置查询条件
        QueryWrapper<Category> categoryQueryWrapper = new QueryWrapper<>();
        categoryQueryWrapper.eq("isDelete", "0");
        categoryQueryWrapper.eq("id", categoryId);
        // 执行查询
        Category category = categoryMapper.selectOne(categoryQueryWrapper);
        if (category == null) {
            responseJsonBody.setCode(MallConstant.SUCCESS_CODE);
            responseJsonBody.setMsg(MallConstant.SUCCESS_DESC);
            responseJsonBody.setData(null);
            return responseJsonBody;
        }
        // 设置分页与查询条件
        Page<Product> productPage = new Page<>(1, 50);
        QueryWrapper<Product> productQueryWrapper = new QueryWrapper<>();
        productQueryWrapper.eq("isDelete", "0");
        productQueryWrapper.eq("cid", categoryId);
        // 执行查询
        productMapper.selectPage(productPage, productQueryWrapper);
        // 获取查询结果
        List<Product> productList = productPage.getRecords();
        // 模型转换
        List<SimpleProductDTO> simpleProductDTOList = new ArrayList<>();
        for (Product product : productList) {
            simpleProductDTOList.add(new SimpleProductDTO(product.getId(), product.getSubTitle()));
        }
        // 返回数据
        responseJsonBody.setCode(MallConstant.SUCCESS_CODE);
        responseJsonBody.setMsg(MallConstant.SUCCESS_DESC);
        responseJsonBody.setData(simpleProductDTOList);
        return responseJsonBody;
    }
}
