package com.excmmy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.excmmy.bean.Category;
import com.excmmy.bean.Product;
import com.excmmy.bean.Productimage;
import com.excmmy.mapper.CategoryMapper;
import com.excmmy.mapper.ProductMapper;
import com.excmmy.mapper.ProductimageMapper;
import com.excmmy.model.HomeProductDTO;
import com.excmmy.model.ListProductInfoDTO;
import com.excmmy.model.ProductsDetailsDTO;
import com.excmmy.service.ProductService;
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
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductimageMapper productimageMapper;
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    @Cacheable(value = "getListProductInfo")
    public ResponseJsonBody getListProductInfo(
            Integer cateCurrent,
            Integer cateSize,
            Integer productCurrent,
            Integer productSize) {
        ResponseJsonBody responseJsonBody = new ResponseJsonBody();
        List<ListProductInfoDTO> listProductInfoDTOList = new ArrayList<>();
        // 查询分类
        // 设置分页与查询条件
        Page<Category> categoryPage = new Page<>(cateCurrent, cateSize);
        QueryWrapper<Category> categoryQueryWrapper = new QueryWrapper<>();
        categoryQueryWrapper.eq("isDelete", "0");
        // 执行查询
        categoryMapper.selectPage(categoryPage, categoryQueryWrapper);
        // 获取查询结果
        List<Category> categoryList = categoryPage.getRecords();
        // 查询商品
        for(Category category : categoryList) {
            // 设置分页与查询条件
            Page<Product> productPage = new Page<>(productCurrent, productSize);
            QueryWrapper<Product> productQueryWrapper = new QueryWrapper<>();
            productQueryWrapper.eq("cid", category.getId());
            productQueryWrapper.eq("isDelete", "0");
            // 执行查询
            productMapper.selectPage(productPage, productQueryWrapper);
            List<Product> productList = productPage.getRecords();
            // 模型转换
            List<HomeProductDTO> homeProductDTOList = new ArrayList<>();
            // 查询商品图片
            for (Product product : productList) {
                // 设置分页与查询条件
                Page<Productimage> productimagePage = new Page<>(1, 1);
                QueryWrapper<Productimage> productimageQueryWrapper = new QueryWrapper<>();
                productimageQueryWrapper.eq("pid", product.getId());
                productimageQueryWrapper.eq("type", "single");
                productimageQueryWrapper.eq("isDelete", "0");
                // 执行查询
                productimageMapper.selectPage(productimagePage, productimageQueryWrapper);
                List<Productimage> productimageList = productimagePage.getRecords();
                homeProductDTOList.add(new HomeProductDTO(product.getId(), product.getName(), product.getPromotePrice(), productimageList.get(0).getId()));
            }
            listProductInfoDTOList.add(new ListProductInfoDTO(category.getId(), category.getName(),homeProductDTOList));
        }
        // 返回数据
        responseJsonBody.setCode(MallConstant.SUCCESS_CODE);
        responseJsonBody.setMsg(MallConstant.SUCCESS_DESC);
        responseJsonBody.setData(listProductInfoDTOList);
        return responseJsonBody;
    }

    @Override
    @Cacheable(value = "getListProductInfo", key = "'productId=' + #id")
    public ResponseJsonBody getProductDetails(Integer id) {
        ResponseJsonBody responseJsonBody = new ResponseJsonBody();
        // 设置查询条件
        QueryWrapper<Product> productQueryWrapper = new QueryWrapper<>();
        productQueryWrapper.eq("id", id);
        productQueryWrapper.eq("isDelete", "0");
        // 执行查询
        Product product = productMapper.selectOne(productQueryWrapper);
        if (product == null) {
            responseJsonBody.setCode(MallConstant.SUCCESS_CODE);
            responseJsonBody.setMsg(MallConstant.SUCCESS_DESC);
            return responseJsonBody;
        }
        // 模型转换
        ProductsDetailsDTO productsDetailsDTO = new ProductsDetailsDTO(
                product.getId(),
                product.getName(),
                product.getSubTitle(),
                product.getOriginalPrice(),
                product.getPromotePrice(),
                product.getStock(),
                product.getSales());
        // 返回数据
        responseJsonBody.setCode(MallConstant.SUCCESS_CODE);
        responseJsonBody.setMsg(MallConstant.SUCCESS_DESC);
        responseJsonBody.setData(productsDetailsDTO);
        return responseJsonBody;
    }
}
