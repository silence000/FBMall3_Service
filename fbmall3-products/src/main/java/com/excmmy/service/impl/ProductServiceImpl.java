package com.excmmy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.excmmy.bean.Category;
import com.excmmy.bean.Product;
import com.excmmy.bean.Productimage;
import com.excmmy.feign.ReviewsServerFeign;
import com.excmmy.mapper.CategoryMapper;
import com.excmmy.mapper.ProductMapper;
import com.excmmy.mapper.ProductimageMapper;
import com.excmmy.model.*;
import com.excmmy.service.ProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import pojo.MallConstant;
import pojo.ResponseJsonBody;

import java.util.*;

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
    @Autowired
    private ReviewsServerFeign reviewsServerFeign;

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
    @Cacheable(value = "getProductDetails", key = "'productId=' + #id")
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

    @Override
    @Cacheable(value = "getProductByConditions")
    public ResponseJsonBody getProductByConditions(String name, Integer cid, Integer current, Integer size, String sortType, Integer low, Integer high) {
        ResponseJsonBody responseJsonBody = new ResponseJsonBody();
        // 设置分页与查询条件
        Page<Product> productPage = new Page<>(current, size);
        QueryWrapper<Product> productQueryWrapper = new QueryWrapper<>();
        if (cid != 0) {
            productQueryWrapper.eq("cid", cid);
        }
        if (!Objects.equals(name, "nul")) {
            productQueryWrapper.like("name", name);
        }
        if (cid == 0 && Objects.equals(name, "nul")) {
            responseJsonBody.setCode(MallConstant.SUCCESS_CODE);
            responseJsonBody.setMsg(MallConstant.SUCCESS_DESC);
            return responseJsonBody;
        }
        productQueryWrapper.eq("isDelete", 0);
        productQueryWrapper.between("promotePrice", low, high);
        if (Objects.equals(sortType, "none")) {
            // todo 默认排序综合排序, 此处应有算法支持综合排序
        }
        if (Objects.equals(sortType, "popularity")) {
            // todo 应该根据评论去排序, 但评论在不同的表里, 此处先用销量代替
            productQueryWrapper.orderByDesc("sales");
        }
        if (Objects.equals(sortType, "sales")) {
            productQueryWrapper.orderByDesc("sales");
        }
        if (Objects.equals(sortType, "price")) {
            productQueryWrapper.orderByAsc("promotePrice");
        }
        if (Objects.equals(sortType, "date")) {
            productQueryWrapper.orderByDesc("createDate");
        }
        // 执行查询
        productMapper.selectPage(productPage, productQueryWrapper);
        // 获取查询结果
        List<Product> productList = productPage.getRecords();
        if (productList.size() == 0) {
            responseJsonBody.setCode(MallConstant.SUCCESS_CODE);
            responseJsonBody.setMsg(MallConstant.SUCCESS_DESC);
            return responseJsonBody;
        }
        // 模型转换
        List<CategoryProductDTO> categoryProductDTOList = new ArrayList<>();
        // 查询商品的图片
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
            // 获取商品评论数量
            ResponseJsonBody reviewsReviews = reviewsServerFeign.getReviewsNumber(product.getId());
            ObjectMapper objectMapper = new ObjectMapper();
            ReviewNumberDTO reviewNumberDTO = objectMapper.convertValue(reviewsReviews.getData(), ReviewNumberDTO.class);
            categoryProductDTOList.add(new CategoryProductDTO(product.getId(), product.getName(), product.getPromotePrice(), productimageList.get(0).getId(), product.getSales(), reviewNumberDTO.getReviewNumber()));
        }
        responseJsonBody.setCode(MallConstant.SUCCESS_CODE);
        responseJsonBody.setMsg(MallConstant.SUCCESS_DESC);
        responseJsonBody.setData(categoryProductDTOList);
        return responseJsonBody;
    }
}
