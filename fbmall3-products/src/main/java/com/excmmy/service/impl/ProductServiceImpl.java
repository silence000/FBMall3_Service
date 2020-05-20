package com.excmmy.service.impl;

import com.excmmy.bean.Product;
import com.excmmy.mapper.ProductMapper;
import com.excmmy.service.ProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
