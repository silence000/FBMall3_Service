package com.excmmy.service;

import com.excmmy.bean.Productimage;
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
public interface ProductimageService extends IService<Productimage> {
    ResponseJsonBody getProductImages(Integer id, String type);
}
