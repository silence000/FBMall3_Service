package com.excmmy.service;

import com.excmmy.bean.Product;
import com.baomidou.mybatisplus.extension.service.IService;
import pojo.ResponseJsonBody;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Excmmy
 * @since 2020-05-20
 */
public interface ProductService extends IService<Product> {
    ResponseJsonBody getListProductInfo(Integer current, Integer size, Integer cateCurrent, Integer cateSize);
    ResponseJsonBody getProductDetails(Integer id);
}
